package Interpreter_Java;

public class TokenException extends Exception{
    String message;
    int line;

    public TokenException(String message, int line) {
        this.message = message;
        this.line = line;
    }

    public String toString(){
        return message + " at line " + line;
    }
}
