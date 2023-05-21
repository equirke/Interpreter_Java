package Interpreter_Java.ast;

import java.util.List;

public class Block  extends Stmt{
    public final List<Stmt> stmts;

    public Block(List<Stmt> stmts){
        this.stmts = stmts;
    }
    public void execute(Visitor visitor){
        visitor.executeBlock(this);
    }
}
