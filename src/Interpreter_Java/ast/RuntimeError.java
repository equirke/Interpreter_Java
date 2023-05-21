package Interpreter_Java.ast;

import Interpreter_Java.Token;

public class RuntimeError extends RuntimeException {
    public final Token token;

    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }

    public String toString(){
        return token.token + " " + getMessage();
    }
}
