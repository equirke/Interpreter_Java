package Interpreter_Java.ast;

import Interpreter_Java.Token;

public class BinaryOp extends ASTNode{
    public final ASTNode left;
    public final Token operator;
    public final ASTNode right;

    public BinaryOp(ASTNode left, Token operator, ASTNode right){
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Object visit(Visitor visitor){
        Object o = visitor.visitBinaryOp(this);
        return o;
    }
}
