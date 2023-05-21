package Interpreter_Java.ast;

import Interpreter_Java.Token;

public class VarStmt extends Stmt {
    final Token name;
    final ASTNode init;

    public VarStmt(Token name, ASTNode init){
        this.name = name;
        this.init = init;
    }

    public void execute(Visitor visitor){
        visitor.executeVarStmt(this);
    }
}
