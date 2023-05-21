package Interpreter_Java;

public class Token {
    final public String token;
    final public Object literal;
    final public TokenType type;
    final public int line;

    public Token(String token, Object literal, TokenType type, int line) {
        this.token = token;
        this.literal = literal;
        this.type = type;
        this.line = line;
    }

    public String toString(){
        return type + " " + token + " " + literal;
    }
}
