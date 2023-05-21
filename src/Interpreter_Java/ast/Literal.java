package Interpreter_Java.ast;

import Interpreter_Java.Token;

public class Literal extends ASTNode{
    public final Token value;
    public Literal(Token value){
        this.value = value;
    }

    public Object visit(Visitor visitor){
        Object o = visitor.visitLiteral(this);
        return o;
    }
}
