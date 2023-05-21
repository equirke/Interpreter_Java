package Interpreter_Java.ast;

public interface Visitor {
    Object visitUnaryOp(UnaryOp u);
    Object visitBinaryOp(BinaryOp bo);
    Object visitLiteral(Literal l);
    Object visitVariable(Variable v);
    Object visitAssign(Assign a);
    void executeVarStmt(VarStmt v);
    void executePrintStmt(PrintStmt p);
    void executeExprStmt(ExprStmt e);
    void executeBlock(Block b);
    void executeIfStmt(IfStmt i);
    void executeWhileStmt(WhileStmt w);
}
