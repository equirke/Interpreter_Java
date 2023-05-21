package Interpreter_Java;

public enum TokenType {
    //Literals and variables
    STRING, NUMBER, IDENT,

    //Operators
    LPAREN, RPAREN,
    PLUS, MINUS, STAR,
    SLASH, MODULUS, SEMICOLON, COMMA,
    ASSIGN, EQ, NEQ, GT, LT,
    GE, LE, BANG,

    //Keywords
    VAR, TRUE, FALSE,
    PRINT, IF, ELSE,
    WHILE,

    //Block
    LBRACE, RBRACE,

    //EOF
    EOF
}
