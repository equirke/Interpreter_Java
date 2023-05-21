package Interpreter_Java.ast;

public class IfStmt extends Stmt {
    public final ASTNode condition;
    public final Stmt statementIfTrue;
    public final Stmt statementIfFalse;

    public IfStmt(ASTNode condition, Stmt statementIfTrue, Stmt statementIfFalse){
        this.condition = condition;
        this.statementIfTrue = statementIfTrue;
        this.statementIfFalse = statementIfFalse;
    }

    public void execute(Visitor v){
        v.executeIfStmt(this);
    }

}
