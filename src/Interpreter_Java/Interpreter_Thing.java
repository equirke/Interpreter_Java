package Interpreter_Java;

import Interpreter_Java.ast.Interpreter;
import Interpreter_Java.ast.RuntimeError;
import Interpreter_Java.ast.Stmt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Interpreter_Thing {
    public static void  main(String[] args){
        try{
            if (args.length > 1) {
                System.out.println("Usage: Interpreter_Thing [script]");
            } else if (args.length == 1) {
                runFile(args[0]);
            } else {
                runPrompt();
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        Interpreter interpreter = new Interpreter();

        for (;;) {
            System.out.print("> ");
            run(reader.readLine(), interpreter);
        }
    }

    private static void run(String source, Interpreter interpreter) {
        Tokeniser tokeniser = new Tokeniser(source);
        try {
            List<Token> tokens = tokeniser.scanTokens();
            Parser parser = new Parser(tokens);

            // For now, just print the tokens.
            /*for (Token token : tokens) {
                System.out.println(token);
            }*/
            try {
                List<Stmt> n = parser.parse();

                try{
                    interpreter.interpret(n);
                }
                catch (RuntimeError e){
                    System.out.println(e);
                    System.exit(1);
                }
            } catch (ParseException e) {
                System.out.println(e);
            }
        }
        catch (TokenException e){
            System.out.println(e);
        }

    }

    private static void run(String source) {
        Tokeniser tokeniser = new Tokeniser(source);
        Interpreter interpreter = new Interpreter();
        try {
            List<Token> tokens = tokeniser.scanTokens();
            Parser parser = new Parser(tokens);

            // For now, just print the tokens.
            for (Token token : tokens) {
                System.out.println(token);
            }
            try {
                List<Stmt> n = parser.parse();

                interpreter.interpret(n);
            } catch (ParseException e) {
                System.out.println(e);
            }
        }
        catch (TokenException e){
            System.out.println(e);
        }

    }
}
