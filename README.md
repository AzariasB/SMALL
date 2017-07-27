SMALL
=====

Small is a programming language based on the JVM instructions.
It will compile to java bytecode and therefore is runnable on any JVM.

It uses jasmin instructions.

Created for a university project.

Instructions
----------------
| |Grammar|
|-|-------|
|Number | [0-9]+
|Name | [a-zA-Z_\$]+
|AddOperator | [+-]
|RelationOperator | <
| | <=
| | ==
| | !=
| | >=
| | >
| ShiftOperator | <<
| | >>
| | >>>
| MultiplicationOperator | *
| | /
| | %
|Term | '(' *Expression* ')'
| | *Name*
| | *Number*
| | '-' *Term*
| | '[' *Expression* ']'
| | 'vrai' (true)
| | 'faux' (false)
| | 'aentier' (to int)
| | 'achaine' (to string)
| | 'lon' (length)
|Expression | *Turnary Expression*
|TurnaryExpression | *Relop Expression* [? *Expression * : *Expression*]
|RelopExpression | *AddExpression* [*RelationOperator* *AddExpression*]
|AddExpressoin | *ShiftExpression* [*AddOperator* *MultiplicationExpression*]
|ShiftExpression | *MultiplicationExpression* [*ShiftOperator* *Term*]
|MultiplicationExpression | *Term* [*MultiplicationOperator* *Term*]
|IfStatement | 'si' *Expression* 'alors' *StatementList* 'fin'
|WhileStatement | 'tantque' *Expression* 'faire' *StatementList* 'fin'
|DoStatement | 'faire' *StatementList* 

Authors
-----------
- Simon
- Azarias

Licence
----------
GNU GPL3