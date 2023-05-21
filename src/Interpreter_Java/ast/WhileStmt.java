package Interpreter_Java.ast;

public class WhileStmt extends Stmt{
    public final ASTNode condition;
    public final Stmt statementWhileTrue;


    public WhileStmt(ASTNode condition, Stmt statementWhileTrue){
        this.condition = condition;
        this.statementWhileTrue = statementWhileTrue;
    }

    public void execute(Visitor visitor){
        visitor.executeWhileStmt(this);
    }
}
