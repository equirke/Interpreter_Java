package Interpreter_Java;

import Interpreter_Java.ast.*;

import java.util.ArrayList;
import java.util.List;

import static Interpreter_Java.TokenType.*;

public class Parser {
    private List<Token> tokens;
    private int current;


    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        current = 0;
    }

    private Token peek(){
        if(current < tokens.size())
            return tokens.get(current);
        return null;
    }

    private boolean isAtEnd(){
        return tokens.get(current).type == EOF;
    }

    private Token peekNext() {
        if (current + 1 >= tokens.size()) return null;
        return tokens.get(current + 1);
    }

    private Token advance(){
        current++;
        if(current >= tokens.size()) return null;
        return tokens.get(current - 1);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(TokenType type, String message) throws ParseException {
        if (check(type)) return advance();
        throw new ParseException(message, peek().type, peek().line);
    }
    private boolean check(TokenType tokenType) {
        if (isAtEnd()) return false;
        return peek().type == tokenType;
    }

    private Token previous(){
        return tokens.get(current - 1);
    }

    public List<Stmt> parse() throws ParseException{
        List<Stmt> statements = new ArrayList<>();
        while(!isAtEnd()){
            statements.add(statement());
        }
        return statements;
    }

    private Stmt statement() throws ParseException{
        Stmt stmt = null;
        if(match(VAR)){
            stmt = varDeclaration();
        }
        else if(match(PRINT)){
            stmt = printStatement();
        }
        else if(match(LBRACE)){
            stmt = block();
        }
        else if(match(IF)){
            stmt = ifStatement();
        }
        else if(match(WHILE)){
            stmt = whileStatement();
        }
        else {
            stmt = exprStatement();
        }

        return stmt;
    }

    private Stmt varDeclaration() throws ParseException{
        Token name = consume(IDENT, "Expected identifier ");

        ASTNode init = null;
        if(match(ASSIGN)){
            init = expression();
        }

        consume(SEMICOLON, "Expect ; after var declaration");
        return new VarStmt(name, init);
    }

    private Stmt printStatement() throws ParseException{
        List<ASTNode> exprs = new ArrayList<>();
        exprs.add(expression());
        while(match(COMMA)){
            exprs.add(expression());
        }

        consume(SEMICOLON, "Expect ; after print statement");
        return new PrintStmt(exprs);
    }

    public Stmt exprStatement() throws ParseException{
        ASTNode expr = expression();

        consume(SEMICOLON, "Expect ' after expression statement");
        return new ExprStmt(expr);
    }

    private Stmt block() throws ParseException{
        List<Stmt> stmts = new ArrayList<>();
        while(!isAtEnd()){
            if(check(RBRACE)) break;
            stmts.add(statement());
        }

        consume(RBRACE, "Expected } after block");
        return new Block(stmts);
    }

    private Stmt ifStatement() throws ParseException{
        consume(LPAREN, "Expect ( before condition");
        ASTNode expr = expression();
        consume(RPAREN, "Expected ) after condition");
        Stmt ifTrue = statement();
        Stmt ifFalse = null;
        if(match(ELSE)){
            ifFalse = statement();
        }

        return new IfStmt(expr, ifTrue, ifFalse);
    }

    private Stmt whileStatement() throws ParseException{
        consume(LPAREN, "Expect ( before condition");
        ASTNode expr = expression();
        consume(RPAREN, "Expected ) after condition");
        Stmt whileTrue = statement();

        return new WhileStmt(expr, whileTrue);
    }

    public ASTNode expression() throws ParseException{
         ASTNode expr = assign();
         return expr;
    }

    public ASTNode assign() throws ParseException{
        ASTNode expr = equality();

        if(match(ASSIGN)){
            Token equals = previous();
            ASTNode value = assign();

            if(expr instanceof Variable){
                Token name = ((Variable)expr).var;
                return new Assign(name, value);
            }

            else throw new ParseException("Invalid Assignment", equals.type, equals.line);
        }

        return expr;
    }

    private ASTNode equality() throws ParseException{
        ASTNode comparison = comparison();
        while(match(EQ, NEQ)){
            Token operator = previous();
            ASTNode right = comparison();
            comparison = new BinaryOp(comparison, operator, right);
        }

        return comparison;
    }

    private ASTNode comparison() throws ParseException{
        ASTNode addition = addition();
        while(match(GT, GE, LT, LE)){
            Token operator = previous();
            ASTNode right = addition();
            addition = new BinaryOp(addition, operator, right);
        }

        return addition;
    }

    private ASTNode addition() throws ParseException{
        ASTNode term = multiplication();
        while(match(PLUS, MINUS)){
            Token operator = previous();
            ASTNode right = multiplication();
            term = new BinaryOp(term, operator, right);
        }

        return term;
    }

    private ASTNode multiplication() throws ParseException{
        ASTNode factor = unary();
        while(match(SLASH, STAR, MODULUS)){
            Token operator = previous();
            ASTNode right = unary();
            factor = new BinaryOp(factor, operator, right);
        }

        return factor;
    }

    private ASTNode unary() throws ParseException{
        if(match(BANG, MINUS)){
            Token operator = previous();
            ASTNode right = unary();
            return new UnaryOp(operator, right);
        }

        return literal();
    }
    private ASTNode literal() throws ParseException{
        ASTNode factor = null;

        switch (peek().type){
            case NUMBER:
                factor = new Literal(peek());
                advance();
                break;
            case IDENT:
                factor = new Variable(peek());
                advance();
                break;
            case STRING:
                factor = new Literal(peek());
                advance();
                break;
            case LPAREN:
                consume(LPAREN, "Expected ( ");
                factor = expression();
                consume(RPAREN, "Expected ) ");
                break;
            default:
                throw new ParseException("Expecting literal or variable", peek().type, peek().line);
        }

        return factor;
    }


}
