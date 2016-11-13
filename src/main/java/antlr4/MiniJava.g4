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
  |   ID '[' (INT | ID) ']' '=' expr ';'
        # arrayDefinition
  |   'while' '(' condExpr ')' statement
        # whileLoopHead
  |   'if' '(' condExpr ')' statement 'else' statement
        # ifStatement
  ;
condExpr
  :   '(' condExpr ')'
  # parenExpr
  |   '!' condExpr
  # notExpr
  |   condExpr '<' condExpr
  # lessThanExpr
  |   condExpr '&&' condExpr
  # andExpr
  |   expr
  # notBoolExpr
  ;
expr
  :   atom ( '+' atom | '-' atom | '*' atom )*
  |   ('+' | '-') atom
  |   atom '[' expr ']'
  |   atom '.' 'length'
  |   atom '.' ID '(' ( condExpr ( ',' condExpr )* )? ')'
  ;
atom : ( INT
  | (ID | 'this') ('.' ID '(' expr ')')?
  | 'new' ID '(' ')'
  | 'this'
  | 'new' 'int' '[' expr ']'
  | '(' expr ')')
  ;

ID        :   [a-zA-Z_][a-zA-Z0-9_]*;
INT       :   '0'..'9'+ ;
BOOLEAN   :   ('true' | 'false') ;
WS        :   [ \t\r\n]+ -> skip ;
COMMENT   : '/*' .*? '*/' -> skip ;
LINE_COMMENT
          : '//' .*? '\r'? '\n' -> skip;
