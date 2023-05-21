package Interpreter_Java;

public class ParseException extends Exception{
    private String message;
    private TokenType type;
    private int line;

    public ParseException(String message, TokenType type, int line) {
        super();
        this.message = message;
        this.type = type;
        this.line = line;
    }

    public String toString(){
        return message + " at line " + line + " but got " + type;
    }
}
