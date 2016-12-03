grammar MiniJava;

prog :  mainClass classDecl*
       ;
mainClass
  :   'class' ID '{' 'public' 'static' 'void' 'main'
                '(' 'String' '[' ']' ID ')' '{' varDecl* statement* '}' '}'
  ;
classDecl
  :   'class' ID '{' propertyDecl* methodDecl* '}'
        # baseClass
  |   'class' ID 'extends' ID '{' propertyDecl* methodDecl* '}'
        # childClass
  |   'case' 'class' ID '(' (caseProperty (',' caseProperty+)*)? ')' ';'
        # caseClassDecl
        ;
varDecl : type ID ';'
        ;
propertyDecl : type ID ';'
        ;
methodDecl :
        'public' type ID '(' (methodParam (',' methodParam+)*)? ')'
        '{' varDecl* statement* 'return' expr ';' '}'
        ;
methodParam : type ID
        ;
caseProperty: type ID
        ;
type  :   'int'
  |   'int' '[' ']'
  |   'boolean'
  |   ID
        ;
statement
  :   '{' statement* '}'
        # basicBlock
  |   'System.out.println' '(' expr ')' ';'
        # printToConsole
  |   ID '=' expr ';'
        # varDefinition
  |   ID '[' expr ']' '=' expr ';'
        # arrayDefinition
  |   'while' '(' expr ')' statement
        # whileLoopHead
  |   'if' '(' expr ')' statement 'else' statement
        # ifStatement
  ;

expr
  : expr PLUS expr
  # plusExpression
  |   expr MINUS expr
  # subtractExpression
  |   expr MULT expr
  # multiplyExpression
  |   atom '[' expr ']'
  # arrayAccessExpression
  |   expr '.' 'length'
  # arrLenExpression
  |   expr '.' ID '(' ( expr ( ',' expr )* )? ')'
  # methodCallExpression
  | '(' expr ')'
  # parenExpr
  |   '!' expr
  # notExpr
  |   expr LESS_THAN expr
  # lessThanExpr
  |   expr GREAT_THAN expr
  # greaterThanExpr
  |   expr '&&' expr
  # andExpr
  |   atom
  # atomExpr
  ;

PLUS: '+';
MINUS: '-';
MULT: '*';
LESS_THAN: '<';
GREAT_THAN: '>';
AND: '&&';

atom :
  INT_LIT
  # intLiteral
  | ID
  # idLiteral
  | 'new' ID '(' ')'
  # constructorCall
  | 'this'
  # thisCall
  | 'new' 'int' '[' expr ']'
  # integerArr
  | BOOLEAN_LIT
  # booleanLit
  ;

ID        :   [a-zA-Z_][a-zA-Z0-9_]*;
INT_LIT       :   '0'..'9'+ ;
BOOLEAN_LIT   :   ('true' | 'false') ;
WS        :   [ \t\r\n]+ -> skip ;
COMMENT   : '/*' .*? '*/' -> skip ;
LINE_COMMENT
          : '//' .*? '\r'? '\n' -> skip;
THIS: (ID | 'this');