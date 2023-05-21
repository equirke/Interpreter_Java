package Interpreter_Java.ast;

import java.util.List;

public class Interpreter implements Visitor{

    private Scope scope =  new Scope();

    private boolean isEqual(Object a, Object b) {
        // nil is only equal to nil.
        if (a == null && b == null) return true;
        if (a == null) return false;

        return a.equals(b);
    }

    public Object visitBinaryOp(BinaryOp bo){
        Object left = bo.left.visit(this);
        Object right = bo.right.visit(this);

        switch (bo.operator.type){
            case PLUS:
                if(left instanceof String && right instanceof String){
                    return (String)left + (String) right;
                }
                return (double) left + (double)right;
            case MINUS:
                return (double) left - (double) right;
            case STAR:
                return (double) left * (double) right;
            case SLASH:
                return (double) left / (double) right;
            case MODULUS:
                return (double) left % (double) right;
            case EQ:
                return isEqual(left, right);
            case NEQ:
                return !isEqual(left, right);
            case GT:
                return (double)left > (double)right;
            case GE:
                return (double)left >= (double)right;
            case LT:
                return (double)left < (double)right;
            case LE:
                return (double)left <= (double)right;
        }

        return null;
    }

    public Object visitUnaryOp(UnaryOp u){
        Object right = u.right.visit(this);
        switch (u.operator.type){
            case MINUS:
                return -((double)right);
            case BANG:
                return !(boolean)right;
        }

        return null;
    }

    public Object visitLiteral(Literal l){
        Object o = l.value.literal;
        return o;
    }

    public Object visitVariable(Variable v){
        Object o = scope.get(v.var);
        return o;
    }

    public Object visitAssign(Assign a){
        Object o = a.value.visit(this);
        scope.assign(a.name, o);
        return o;
    }

    public void executeVarStmt(VarStmt v){
        Object o = null;
        if(v.init != null) {
            o = v.init.visit(this);
        }
        scope.declare(v.name, o);
    }

    public void executePrintStmt(PrintStmt p){
        List<ASTNode> exprs = p.exprs;

        for(ASTNode expr :exprs) {
            System.out.println(expr.visit(this));
        }
    }

    public void executeExprStmt(ExprStmt e){
        e.expr.visit(this);
    }

    public void executeBlock(Block b){
        scope = new Scope(scope);
        for(Stmt stmt: b.stmts){
            stmt.execute(this);
        }
        scope = scope.getScopeEnclosed();
    }

    public void executeIfStmt(IfStmt i){
        Boolean b = (Boolean)i.condition.visit(this);

        if(b){
            i.statementIfTrue.execute(this);
        }
        else if(i.statementIfFalse != null){
            i.statementIfFalse.execute(this);
        }
    }

    public void executeWhileStmt(WhileStmt w){
        Boolean b = (Boolean)w.condition.visit(this);
        while(b){
            w.statementWhileTrue.execute(this);
            b = (Boolean)w.condition.visit(this);
        }
    }

    public void interpret(List<Stmt> statements){
        for(Stmt stmt: statements){
            stmt.execute(this);
        }
    }
}
