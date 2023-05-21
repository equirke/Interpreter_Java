package Interpreter_Java.ast;

import Interpreter_Java.Token;

public class Assign extends ASTNode {
    final public Token name;
    final public ASTNode value;

    public Assign(Token name, ASTNode value){
        this.name = name;
        this.value = value;
    }

    @Override
    public Object visit(Visitor visitor) {
        Object o = visitor.visitAssign(this);
        return o;
    }
}
