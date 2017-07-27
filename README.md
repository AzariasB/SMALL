SMALL
=====

Small is a programming language based on the JVM instructions.
It will compile to java bytecode and therefore is runnable on any JVM.

It uses jasmin instructions.

Created for a university project.

Instructions
----------------

Token |Grammar|
-|-------|
Number | [0-9]+
Name | [a-zA-Z_\$]+
AddOperator | [+-]
RelationOperator | (<\|<=\|==\|!=\|>=\|>)
 ShiftOperator | (<<\|>>\| >>>)
 MultiplicationOperator | ( * \| / \| % )
Term | '(' *Expression* ')'
 || *Name*
 || *Number*
 || '-' *Term*
 || '[' *Expression* ']'
 || ('vrai' (true) \| 'faux' (false) \| 'aentier' (to int) \| 'achaine' (to string) ) \| 'faux' (false)
Expression | *Turnary Expression*
TurnaryExpression | *Relop Expression* [? *Expression * : *Expression*]
RelopExpression | *AddExpression* [*RelationOperator* *AddExpression*]
AddExpressoin | *ShiftExpression* [*AddOperator* *MultiplicationExpression*]
ShiftExpression | *MultiplicationExpression* [*ShiftOperator* *Term*]
MultiplicationExpression | *Term* [*MultiplicationOperator* *Term*]
IfStatement | 'si' *Expression* 'alors' *StatementList* ['sinonsi' *Expression* 'alors' *StatementList*] 'fin'
WhileStatement | 'tantque' *Expression* 'faire' *StatementList* 'fin'
DoStatement | 'faire' *StatementList* ('fin' \| 'jusqua' *Expression* )
ReadStatement | 'lire' *Name* [',' *Name*]*
PrintItem | *StringLiteral* \| *Expression*
PrintStatement | 'ecrire' *PrintItem* [',' *PrintItem*]*
Assignement  | *Name* '=' *Expression*
AissgnementList | *Assignement*+
ForStatement | 'pour' *AssignementList* ['tantque' *Expression*]['alors' *AssignementList* ] 'faire' *StatementList* 'fin'

Authors
-----------
- Simon
- Azarias

Licence
----------
GNU GPL3