package Interpreter_Java.ast;

import Interpreter_Java.Token;

public class Variable extends ASTNode {
    public final Token var;

    public Variable(Token var){
        this.var = var;
    }

    public Object visit(Visitor visitor){
        return visitor.visitVariable(this);
    }

}
