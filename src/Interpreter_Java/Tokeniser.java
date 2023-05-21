package Interpreter_Java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Interpreter_Java.TokenType.*;

public class Tokeniser {
    private String source;
    private int  current = 0;
    private int start = 0;
    private static final Map<String, TokenType> keywords;

    static{
        keywords = new HashMap<>();
        keywords.put("var", VAR);
        keywords.put("true", TRUE);
        keywords.put("false", FALSE);
        keywords.put("print", PRINT);
        keywords.put("if", IF);
        keywords.put("else", ELSE);
        keywords.put("while", WHILE);
    }



    int line = 1;
    private List<Token> tokens = new ArrayList<Token>();
    public Tokeniser(String source){
        this.source = source;
    }

    private boolean isAtEnd(){
        return current >= source.length();
    }

    private char advance(){
        current++;
        return source.charAt(current - 1);
    }

    private char peek(){
        if(current < source.length())
            return source.charAt(current);
        return 0;
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return 0;
        return source.charAt(current + 1);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private void addToken(TokenType type){
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal){
        String text = source.substring(start, current);
        tokens.add(new Token(text, literal, type, line));
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isAlphaNumeric(char c){
        return (isDigit(c) || isAlpha(c));
    }

    private void number() {
        while (isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(NUMBER,
                Double.parseDouble(source.substring(start, current)));
    }

    private void string() throws TokenException {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        // Unterminated string.
        if (isAtEnd()) {
            throw new TokenException("End quote missing", line);
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TokenType type =  keywords.get(text);
        if(type == null) type = IDENT;
        addToken(type);
    }

    private void scanToken() throws TokenException {
        char c = advance();
        switch (c){
            case '(': addToken(LPAREN); break;
            case ')': addToken(RPAREN); break;
            case '+': addToken(PLUS); break;
            case '-': addToken(MINUS); break;
            case '*': addToken(STAR); break;
            case '/': addToken(SLASH); break;
            case '%': addToken(MODULUS); break;
            case ';': addToken(SEMICOLON); break;
            case ',': addToken(COMMA); break;
            case '{': addToken(LBRACE); break;
            case '}': addToken(RBRACE); break;
            case '"': string(); break;
            case '=':
                if(match('=')) {
                    addToken(EQ);
                    break;
                }
                addToken(ASSIGN);
            break;
            case '!':
                if(match('=')) {
                    addToken(NEQ);
                    break;
                }
                addToken(BANG);
            break;
            case '>':
                if(match('=')) {
                    addToken(GE);
                    break;
                }
                addToken(GT);
            break;
            case '<':
                if(match('=')) {
                    addToken(LE);
                    break;
                }
                addToken(LT);
            break;
            case '\n': line++; break;
            default:
                if(isDigit(c)){
                    number();
                    break;
                }

                if(isAlpha(c)){
                    identifier();
                    break;
                }
            }
    }

    public List<Token> scanTokens() throws TokenException {
        while(!isAtEnd()){
            start = current;
            scanToken();
        }

        addToken(EOF);

        return tokens;
    }


}
