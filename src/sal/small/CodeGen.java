/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sal.small;

import sal.util.Templater;
import sal.util.ErrorStream;

import java.io.PrintStream;

import static sal.small.Scope.*;
import static sal.small.Descriptor.*;
import static sal.small.Token.*;
import static sal.small.Code.*;

/**
 * Generate Jasmin (Java Assembler) code from a syntax tree.
 *
 * This is the main output program. It walks over the AST. The method
 * {@link #writeProgram(PrintStream,Tree)} takes the syntax tree and embeds it
 * in a 'wrapper' - a Jasmin program with 'housekeeping already done so that all
 * {@link #generate(Tree, Scope)} needs to do is deal with each tree node type
 * as it finds it.
 */
public class CodeGen {

    /**
     * Writes (to the PrintStream provided by CodeWriter) a boilerplate Jasmin
     * program in which is embedded the result of generating code from the AST.
     *
     * @param tree AST which forms the program.
     */
    public static void writeProgram(PrintStream outputStream, Tree<Token> tree) {

        // Code needs to know the output stream  for all small 'emit' methods below.
        Code.setOutputStream(outputStream);

        Templater tr = new Templater(outputStream) {

            String className = getGlobal("CLASS NAME");

            public void toString(String s) {

                switch (s) {

                    case "CLASSNAME":
                        print(className);
                        return;

                    case "CODE":       // we need an inner scope since code is generated as body of main()
                        beginScope();
                        // invent a mythical first arg
                        newLocal("ARGS TO MAIN", Type.ARRAY_STRING /*"[Ljava.lang.String;"*/);
                        writeStatementCode(tree);
                        endScope();
                        return;

                    case "LOCALS":      //Integer n = getGlobal(MAX_LOCAL);
                        print(getGlobal(MAX_LOCAL));

                    default:
                        return;
                }
            }
        };

        // this is the 'boiler plate' code into which generated code is inserted. 
        // it is done this way to make it easier to change - and because Templater already existed!
        String[] theText = new String[]{
            ".class public (~CLASSNAME~)\n\n",
            ".super java/lang/Object\n",
            ".method public <init>()V\n",
            ".limit stack 10\n",
            "    aload_0\n",
            "    invokespecial java/lang/Object/<init>()V\n",
            "    return\n",
            ".end method\n",
            ".method public static main([Ljava/lang/String;)V\n",
            ".limit stack 10\n",
            "(~CODE~)",
            "    return\n",
            ".limit locals (~LOCALS~)\n",
            ".end method\n",
            "\n"};

        for (String aLine : theText) {
            tr.render(aLine);
        }
    }

    public static boolean isStringVar(String name) {
        return getVar(name).type.isString();
    }

    // small method for checking int/string types 
    public static boolean isStringVar(Tree<Token> tree) {
        if (!tree.isLeaf()) {
            return false;
        }
        return isStringVar(tree.toString());
    }

    /**
     * Generate Jasmin assembler from an AST.
     *
     * @param t The AST.
     *
     * Generate writes Jasmin assembler code to the
     * {@link java.io.PrintStream PrintStream} out.
     */
    static void writeStatementCode(Tree<Token> tree) {

        if (tree == null) {
            return;
        }
        Token token = tree.token();

        switch (token) {

            // generate code for a list of statements
            case STATEMENTLIST:
                tree.allChildren().forEach((tst) -> {
                    writeStatementCode(tst);
                });
                return;

            // as STATEMENTLIST but within a new scope
            case BLOCK: {
                beginScope();
                tree.allChildren().forEach((tst) -> {
                    writeStatementCode(tst);
                });
                endScope();
            }
            return;

            // individual statements
            case DECREMENT:
            case INCREMENT: {
                Tree<Token> var = tree.child(0);
                increment(var.toString(), (token == INCREMENT) ? 1 : -1);
            }
            return;

            case ASSIGN: {
                Tree<Token> var = tree.child(0);
                Type stringExp = writeExpressionCode(tree.child(1));
                store(var.toString(), stringExp);
            }
            return;
            case RSQ: {
                Tree<Token> var = tree.child(0);
                writeExpressionCode(var);
                writeExpressionCode(tree.child(1));
                Type assignT = writeExpressionCode(tree.child(2));
                Type t = getVar(var.toString()).type;
                if (t == Type.ARRAY_INT && assignT == Type.INT) {
                    emit("iastore");
                } else if (t == Type.ARRAY_STRING && assignT == Type.STRING) {
                    emit("aastore");
                } else {
                    ErrorStream.log("Tried to assign bad type to array\n");
                }

            }
            return;
            case IF: {
                beginScope();	// start a scope to cover the whole if
                Label endIf = newLabel("END IF");  // label for this end-if 
                int pairs = tree.children();	// (test then code)+
                for (int i = 0; i < pairs; i += 2) {
                    Tree<Token> test = tree.child(i);
                    Tree<Token> code = tree.child(i + 1);
                    if (test != null) {	// not 'else' part
                        Label nextTest = newLabel("NEXT TEST");  // for jump to next elif/else 		
                        writeExpressionCode(test, Type.INT);
                        ifFalse(nextTest);
                        writeStatementCode(code);
                        jump(endIf);
                        setLabel(nextTest);
                    } else {
                        writeStatementCode(code);
                    }
                }
                setLabel(endIf);
                endScope();
            }
            return;

            case WHILE: { // also do/end
                beginScope();
                Label continueLabel = newLabel("NEXT LOOP");
                Label breakLabel = newLabel("EXIT LOOP");
                setLabel(continueLabel);	// jump back here for 'continue'
                Tree<Token> testExpr = tree.child(0);
                if (testExpr != null) {		// 'null' for do/end
                    writeExpressionCode(tree.child(0), Type.INT);  		// expression to test  
                    ifFalse(breakLabel);		// if not true, 'break'
                }
                writeStatementCode(tree.child(1)); 		// content of while/do
                jump(continueLabel);		// jump back to beginning
                setLabel(breakLabel);		// outside while
                endScope();
            }
            return;

            case SWITCH: {
                beginScope();
                Label endSwitch = newLabel("EXIT SWITCH");
                int pairs = tree.children();
                Label afterCase = newLabel("AFTER CASE");
                for (int i = 0; i < pairs; i += 2) {
                    Tree<Token> test = tree.child(i);
                    Tree<Token> code = tree.child(i + 1);
                    if (test != null) {
                        Label nextTest = newLabel("NEXT CASE");
                        writeExpressionCode(test);
                        ifFalse(nextTest);
                        setLabel(afterCase);
                        afterCase = newLabel("AFTER CASE");
                        writeStatementCode(code);
                        jump(afterCase);//Will jump only if no break in statement list
                        setLabel(nextTest);
                    } else {
                        setLabel(afterCase);
                        writeStatementCode(code);
                    }
                }
                setLabel(endSwitch);
                endScope();
            }
            return;

            case UNTIL: {
                beginScope();
                Label continueLabel = newLabel("NEXT LOOP");
                Label breakLabel = newLabel("EXIT LOOP");
                Label startLabel = newLabel("START LOOP");
                setLabel(startLabel); 		//	jump to here exit conmdition isn't met
                writeStatementCode(tree.child(1)); 		// contents of do/until loop
                setLabel(continueLabel);	// 'continue' goes to just before the test

                //!!!!! the following lines replace the lines
                //!!!!!   	writeExpressionCode(tree.child(0), INT_TYPE);  	// code for test
                //!!!!!	  	ifFalse(startLabel);		// if test fails jump back to start
                //!!!!!  in the original version	
                Tree<Token> test = tree.child(0);
                if (test != null) {
                    writeExpressionCode(test, Type.INT);  	// code for test
                    ifFalse(startLabel);		// if test fails jump back to start
                }
                //!!!!!!!!! end of changes
                setLabel(breakLabel);		// or continue here
                endScope();
            }
            return;

            case BREAK: {
                Label l = getLabel("EXIT LOOP");
                if (l == null) {
                    l = getLabel("EXIT SWITCH");
                }
                if (l == null) {
                    ErrorStream.log("'break' used outside of loop or switch.\n");
                } else {
                    jump(l);
                }
            }
            return;
            case CONTINUE: {
                Label l = getLabel("NEXT LOOP");
                if (l == null) {
                    ErrorStream.log("\'break\' or \'continue\' used outside a loop.\n");
                } else {
                    jump(l);
                }
                return;
            }
            case HALT:
                emit("return");
                break;
            case PRINT: {
                boolean isString = writeExpressionCode(tree.child(0)).isString();
                emit(isString ? PRINT_STR : PRINT_INT);
                return;
            }

            case READ_STR:
                emit(token);
                store(tree.toString(), Type.STRING);
                break;
            case READ_INT: {
                emit(token);
                store(tree.toString(), Type.INT);
                break;
            }

        }
    }

    public static void writeExpressionCode(Tree<Token> tree, Type needsString) {
        Type expIsString = writeExpressionCode(tree);
        if (needsString != expIsString) {
            emit(needsString == Type.STRING ? TO_STR : LENGTH);
        }
    }

    public static Type writeExpressionCode(Tree<Token> tree) {

        Token token = tree.token();
        int kids = tree.children();
        if (kids == 0) {
            //  a leaf - must be Number, String or Identifier
            emit(token, tree.toString());
            return (token == NUMBER) ? Type.INT
                    : (token == STRING) ? Type.STRING
                            : isStringVar(tree) ? Type.STRING
                            : Type.ARRAY_STRING;
        } else if (kids == 3) {//turnary
            writeExpressionCode(tree.child(0));
            Label endLabel = newLabel("END QUERY");
            Label falseLabel = newLabel("FALSE LABEL");
            ifFalse(falseLabel);
            writeExpressionCode(tree.child(1));
            jump(endLabel);
            setLabel(falseLabel);
            Type returnType = writeExpressionCode(tree.child(2));
            setLabel(endLabel);
            return returnType;
        }

        // write code for first child and check type
        if (token == LSQ) {//Load from array
            writeExpressionCode(tree.child(0));
            writeExpressionCode(tree.child(1));
            Type arrayType = getVar(tree.child(0).toString()).type;
            if (null == arrayType) {
                ErrorStream.log("Unknown array type");
            } else {
                switch (arrayType) {
                    case ARRAY_INT:
                        emit("iaload");
                        return Type.INT;
                    case ARRAY_STRING:
                        emit("aaload");
                        return Type.STRING;
                    default:
                        ErrorStream.log("Unknown array type");
                        break;
                }
            }
        }

        Type child0 = writeExpressionCode(tree.child(0));
        // Deal with unary operators 
        switch (token) {
            case NEGATE:
                if (child0.isString()) {
                    ErrorStream.log("Attempt to apply \'-\' to a string.\n");
                }
                emit(NEGATE);
                return Type.INT;	// assuming an int was intended!	
            //!!! Insert String operations here !!:
            case TO_INT:
                if (!child0.isString()) {
                    ErrorStream.log("Attempt to apply " + TO_INT.asText + " to an int.\n");
                }
                emit(token);
                return Type.INT;
            case TO_STR:
                if (child0.isString()) {
                    ErrorStream.log("Attempt to apply " + TO_STR.asText + " to a string.\n");
                }
                emit(token);
                return Type.STRING;
            case LENGTH:
                if (!child0.isString()) {
                    ErrorStream.log("Attempt to get " + LENGTH.asText + " of a non-string.\n");
                }
                emit(token);
                return Type.INT;
        }

        Tree<Token> child1 = tree.child(1);

        if (token == NEW_ARRAY) {
            if (null == child1.token) {
                ErrorStream.log("Unknown array type");
                return Type.INT;
            } else {
                switch (child1.token) {
                    case TYPE_INT:
                        emit("newarray int");
                        return Type.ARRAY_INT;
                    case TYPE_STRING:
                        emit("anewarray java/lang/String");
                        return Type.ARRAY_STRING;
                    default:
                        ErrorStream.log("Unknown array type");
                        return Type.INT;
                }
            }
        }

        // Now binary operations
        Type child1Type = writeExpressionCode(child1);

        switch (token) {
            case LE:
            case LT:
            case GE:
            case GT:
            case EQ:
            case NE: {	// first deal with different types
                if (child0.isString()) {
                    if (!child1Type.isString()) {
                        ErrorStream.log(" <string> %s <int> is illegal.\n", token);
                    } else {
                        emit(COMPARE_STR);	// compare strings
                        emit(ZERO);		// to give compare with 0		
                    }
                } else /* child0 is an int */ {
                    if (child1Type.isString()) {
                        ErrorStream.log(" <int> %s <string> is illegal.\n", token);
                    }
                }
                // next deal with code to make an int comparison
                /* if the test is false, the code falls through to the next line
                       where 0 (i.e.false) is loaded onto the stack. There is then a jump to the end.
                       The next line is the target when the test was true, -1 (true) is loaded onto the stack
                 */
                Label ifTrue = newLabel("TRUE VAL");
                Label ifFalse = newLabel("FALSE VAL");
                jump(token, ifTrue);
                emit(ZERO);		// for false
                jump(ifFalse);
                setLabel(ifTrue);
                emit(ONE);		// for true
                setLabel(ifFalse);
            }
            return Type.INT;	// int left on stack

            // String and integer operations
            // !!!!! STRING OPS NOT YET COMPLETE !!!!
            case PLUS:
                if (!child0.isString() && !child1Type.isString()) {
                    emit(PLUS);
                    return Type.INT;
                } else {
                    if (!child0.isString()) {
                        emit(SWAP);
                        emit(TO_STR);
                        emit(SWAP);
                    } else if (!child1Type.isString()) {
                        emit(TO_STR);
                    }
                    emit(CONCAT);
                    return Type.STRING;
                }
            case SHL:
            case SHR:
                if (child1Type.isString()) {
                    ErrorStream.log("Tried to shift with a string");
                    return Type.INT;
                }
                if (!child0.isString()) {
                    emit(token);
                    return Type.INT;
                } else {
                    emit(token == SHL ? LEFT_STR : RIGHT_STR);
                    return Type.STRING;
                }
            case MINUS:
            case TIMES:
            case DIVIDE:
            case MOD:
            case MINIMUM:
            case MAXIMUM:
            case SHRS: {
                emit(token);
            }
            return Type.INT;
        }

        ErrorStream.log("Unexpected token in code generation %s", token.toString());
        return Type.INT; // and why not!
    }

}
