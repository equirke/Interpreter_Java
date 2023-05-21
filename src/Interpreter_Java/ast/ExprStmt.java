package Interpreter_Java.ast;

public class ExprStmt extends Stmt {
    public final ASTNode expr;

    public ExprStmt(ASTNode expr){
        this.expr = expr;
    }

    @Override
    public void execute(Visitor visitor) {
        visitor.executeExprStmt(this);
    }
}
