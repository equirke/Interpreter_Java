package Interpreter_Java.ast;

import java.util.ArrayList;
import java.util.List;

public class PrintStmt extends Stmt{
    List<ASTNode> exprs = new ArrayList<>();

    public PrintStmt(List<ASTNode> exprs){
        this.exprs = exprs;
    }

    public void execute(Visitor visitor) {
        visitor.executePrintStmt(this);
    }
}
