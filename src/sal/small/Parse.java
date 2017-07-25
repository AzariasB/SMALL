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

import java.util.EnumSet;

import static sal.small.Tree.*;
import static sal.small.Main.*;
import static sal.small.Token.*;

/**
 * Reads the source and generates an AST (abstract syntax tree). There is a
 * separate method for each grammar rule. Each method called returns an AST for
 * the statement it has processed. The Syntax rules for each method is given
 * with the method.
 */
public class Parse {

    /**
     * Parse a program. Grammar rule {@code program         : 'program' name ('/' name )*; statementList
     * }
     *
     * @return AST for complete program.
     */
    public static Tree<Token> program() {
        // read the first token from the input
        scan();
        Tree<Token> t = statementList();	// a program consists of a sequence of statements
        mustBe(EOF);
        return t;
    }

    /**
     * Return a list of parsed statements. Grammar rule {@code statementList   : ( ifStatement | whileStatement  | doStatement
     * 											| printStatement | assignment | readStatement | ...   )* }
     *
     * @return AST for statementList.
     */
    public static Tree<Token> statementList() {
        Tree<Token> stList = list(STATEMENTLIST);
        for (;;) {
            while (skipToken(SEMICOLON)) /* do nothing */;
            Tree<Token> aStatement;	// next statement
            Token token = currentToken();
            switch (token) {
                case IF:
                    aStatement = ifStatement();
                    break;
                case WHILE:
                    aStatement = whileStatement();
                    break;
                case DO:
                    aStatement = doStatement();
                    break;
                case PRINT:
                    aStatement = printStatement();
                    break;
                case IDENTIFIER:
                    aStatement = assignment();
                    break;
                case FOR:
                    aStatement = forStatement();
                    break;
                case READ:
                    aStatement = readStatement();
                    break;
                case SWITCH:
                    aStatement = switchStatement();
                    break;
                case FUNCTION:
                    aStatement = functionStatement();
                    break;
                case HALT:
                case BREAK:
                case CONTINUE:
                    aStatement = leaf(token);
                    scan();
                    break;

                default:
                    return stList;	// exit method with list
            }
            // add next statement to list
            stList.addChild(aStatement);
        }
    }

    private static Tree<Token> functionStatement() {

        return null;
    }

    private static Tree<Token> assignmentList() {
        Tree<Token> asList = list(STATEMENTLIST);
        for (;;) {
            while (skipToken(SEMICOLON));
            Token tk = currentToken();
            if (tk == IDENTIFIER) {
                asList.addChild(assignment());
            } else {
                return asList;
            }
        }
    }

    /**
     * Grammar rul {@code forStatement : 'for' assignementList [while expression][then assignementList] 'do' statementList 'end'
     * }
     *
     * @return AST for forStatement
     */
    public static Tree<Token> forStatement() {
        scan(); //'for'
        Tree<Token> body = assignmentList();
        Tree<Token> loop;

        if (skipToken(WHILE)) {
            body.addChild(loop = list(WHILE, expression()));
        } else {
            body.addChild(loop = list(WHILE, (Tree<Token>) null));
        }

        Tree<Token> assignements = null;
        if (skipToken(THEN)) {
            assignements = assignmentList();
        }

        mustBe(DO);
        Tree<Token> statements = statementList();
        if (assignements != null) {
            statements.addChild(assignements);
        }
        loop.addChild(list(STATEMENTLIST, statements));
        mustBe(END);
        return body;
    }

    private static Tree<Token> switchStatement() {
        scan();//'switch'
        Tree<Token> body = list(SWITCH);
        Tree<Token> exp = expression();
        mustBe(DO);
        while (skipToken(CASE)) {
            Tree<Token> tk = expression();
            mustBe(COLON);
            body.addChild(list(EQ, exp, tk));
            body.addChild(statementList());
        }
        if (skipToken(DEFAULT)) {
            mustBe(COLON);
            body.addChild(null);
            body.addChild(statementList());
        }

        mustBe(END);
        return body;
    }

    /**
     * Grammar rule  {@code   ifStatement     : 'if' expression 'then' statementList 'end' }
     *
     * @return AST for ifStatement
     */
    public static Tree<Token> ifStatement() {
        scan(); // skip the 'if' token
        Tree<Token> t = expression();
        boolean optionnalEnd;
        if (optionnalEnd = tokenIn(CONTINUE, BREAK)) {
            Tree<Token> inst = list(STATEMENTLIST, statementList());
            t = list(IF, t, inst);
        } else {
            mustBe(THEN);
            t = list(IF, t, statementList());
        }

        // insert code for any number of 'elif's here
        while (skipToken(ELIF)) {
            t.addChild(expression());
            if (optionnalEnd = tokenIn(CONTINUE, BREAK)) {
                Tree<Token> inst = list(STATEMENTLIST);
                inst.addChild(leaf(currentToken()));
                scan();
                t.addChild(inst);
            } else {
                mustBe(THEN);
                t.addChild(statementList());
            }
        }

        if (skipToken(ELSE)) {
            t.addChild(null);			 // 'no test'
            t.addChild(statementList()); // 'else' statements
            optionnalEnd = false;
        }

        if (!optionnalEnd) {
            mustBe(END);
        }//No check for end
        return t;
    }

    /**
     * Grammar rule  {@code   whileStatement     : 'while' expression 'do' statementList 'end' }
     *
     * @return AST for ifStatement
     */
    public static Tree<Token> whileStatement() {
        scan(); // skip the 'while' token
        Tree<Token> t = expression();
        mustBe(DO);
        t = list(WHILE, t, statementList());
        mustBe(END);
        return t;
    }

    /**
     * Grammar rule {@code untilStatement : 'until' expression 'do' statementList 'end'
     * }
     *
     * @return AST for untilStatement
     */
    public static Tree<Token> untilStatement() {
        scan();// skip the 'until' token
        Tree<Token> t = expression();
        mustBe(DO);
        t = list(UNTIL, t, statementList());
        mustBe(END);
        return t;
    }

    /**
     * Grammar rule  {@code   doStatement     : 'do' statementList ( 'end' | 'until' expression ) }
     *
     * @return AST for ifStatement
     */
    public static Tree<Token> doStatement() {
        scan();// skip 'do' token
        Tree<Token> t = statementList();
        if (skipToken(UNTIL)) {
            return list(UNTIL, expression(), t);
        }
        mustBe(END);
        return list(WHILE, null, t);
    }

    /**
     * Grammar rule {@code   readStatement   : 'read' name (, name)* }
     *
     * @return AST for read statement The read statement is made into a list of
     * individual reads
     */
    public static Tree<Token> readStatement() {
        scan();  // skip the 'print' token
        Tree<Token> readList = list(STATEMENTLIST);
        do {
            String name = currentText();
            mustBe(IDENTIFIER);
            readList.addChild(leaf(isStringName(name) ? READ_STR : READ_INT, name));
        } while (skipToken(COMMA));
        return readList;
    }

    /**
     * Grammar rules {@code printStatement  : 'print' printItem (',' printItem )*
     * }
     * and {@code printItem       : stringLiteral | expression }
     *
     * @return AST for print statement
     *
     * The print statement is made into a list of individual prints
     */
    public static Tree<Token> printStatement() {
        scan(); // skip the word 'print'
        Tree<Token> printList = list(STATEMENTLIST);
        do {
            Tree<Token> printExpr;
            if (currentToken() == STRING) {
                printExpr = leaf(STRING, currentText());
                scan();
            } else {
                printExpr = expression();
            }
            printList.addChild(list(PRINT, printExpr));
        } while (skipToken(COMMA));

        return printList;
    }

    /**
     * Grammar rule {@code assignStatement : name '=' expression }
     *
     * @return AST.
     */
    public static Tree<Token> assignment() {
        Tree<Token> t = leaf(IDENTIFIER, currentText());
        Token token = scan();
        if (skipToken(INCREMENT, DECREMENT)) {
            t = list(token, t);
        } else {
            mustBe(ASSIGN); // skip over the = token
            t = list(ASSIGN, t, expression());
        }
        return t;
    }

    /**
     * Grammar rule {@code expression      : relopExpression }
     *
     * @return AST.
     */
    public static Tree<Token> expression() {
        return turnaryExpression();
    }

    /**
     * Grammar rule
     * {@code turnaryExpression : relopExpression ? expression : expression}
     *
     * @return
     */
    private static Tree<Token> turnaryExpression() {
        Tree<Token> t = relopExpression();
        if (skipToken(QUERY)) {
            Tree<Token> ifTrue = expression();
            mustBe(COLON);
            Tree<Token> ifFalse = expression();
            return list(QUERY, t, ifTrue, ifFalse);
        } else {
            return t;
        }
    }

    // start with lowest priority <, <= etc
    private static final EnumSet<Token> RELATIONALOPS = EnumSet.of(LE, LT, GE, GT, EQ, NE);

    /**
     * Grammar rule {@code  relopExpression : addExpression [ ('<' | '<=' | '==' | '!=' | '<=' | '<' ) addExpression ]
     * }
     *
     * @return AST.
     */
    public static Tree<Token> relopExpression() {
        Tree<Token> t = addExpression();
        Token tok = currentToken();
        if (RELATIONALOPS.contains(tok)) {
            scan();
            t = list(tok, t, addExpression());
        }
        return t;

    }

    private static final EnumSet<Token> ADDOPS = EnumSet.of(PLUS, MINUS);

    /**
     * Grammar rule
     * {@code addExpression   : shiftExpression ( ('+' | '-') multExpression )*}
     *
     * @return AST.
     */
    public static Tree<Token> addExpression() {
        Tree<Token> t = shiftExpression();
        for (Token tok = currentToken(); ADDOPS.contains(tok); tok = currentToken()) {
            scan();
            t = list(tok, t, shiftExpression());
        }
        return t;
    }

    private static final EnumSet<Token> SHIFTOPS = EnumSet.of(SHR, SHL, SHRS);

    /**
     * Grammar rule {@code shiftExpression : multExpression ( ('>>' | '<<' | '>>>' ) multExpression )*
     * }
     *
     * @return AST.
     */
    public static Tree<Token> shiftExpression() {
        Tree<Token> t = multExpression();
        for (Token tok = currentToken(); SHIFTOPS.contains(tok); tok = currentToken()) {
            scan();
            t = list(tok, t, multExpression());
        }
        return t;
    }

    private static final EnumSet<Token> MULTOPS = EnumSet.of(TIMES, DIVIDE, MOD);

    /**
     * Grammar rule {@code multExpression  : term ( ('*' | '/' | '%' ) term )* }
     *
     * @return AST.
     */
    public static Tree<Token> multExpression() {
        Tree<Token> t = term();
        for (Token tok = currentToken(); MULTOPS.contains(tok); tok = currentToken()) {
            scan();
            t = list(tok, t, term());
        }
        return t;
    }

    /**
     * Grammar rule {@code term            : '(' expression ')' | name | number | '-' term}
     *
     * @return AST.
     */
    public static Tree<Token> term() {
        Token token = currentToken();
        String value = currentText();
        Tree<Token> t = null;
        switch (token) {
            case LP:
                scan();    // get next token
                t = expression();
                mustBe(RP);
                return t;
            case IDENTIFIER:
            case STRING:
                t = leaf(token, value);
                break;
            case NUMBER: {
                if (value.charAt(0) == '#') {
                    // convert string after '#' to binary, then back to decimal as a string
                    value = Long.toString(Long.valueOf(value.substring(1), 16));
                }
                t = leaf(token, value);
                break;
            }

            case TRUE:
                t = leaf(NUMBER, "1");
                break;
            case FALSE:
                t = leaf(NUMBER, "0");
                break;
            case MINUS:
                scan();
                return list(NEGATE, term());
            case TO_INT:
            case TO_STR:
            case LEN_STR:
                scan();	// step over operator
                return list(token, term());
            default:
                mustBe(IDENTIFIER,
                        NUMBER,
                        MINUS,
                        TRUE,
                        FALSE,
                        LP,
                        TO_INT,
                        TO_STR,
                        LEN_STR,
                        STRING
                );  // didn't find the start of an expression - there has to be one;
                break;

        }
        scan();
        return t;
    }

}
