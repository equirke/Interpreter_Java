package Interpreter_Java.ast;

import Interpreter_Java.Token;

public class UnaryOp extends ASTNode {
    public final Token operator;
    public final ASTNode right;

    public UnaryOp(Token operator, ASTNode right){
        this.operator = operator;
        this.right = right;
    }

    public Object visit(Visitor visitor){
        Object o = visitor.visitUnaryOp(this);
        return o;
    }
}
