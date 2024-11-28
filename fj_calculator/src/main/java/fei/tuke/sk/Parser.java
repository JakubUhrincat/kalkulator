package fei.tuke.sk;

import java.io.IOException;
import java.util.Scanner;

public class Parser {
    private Token symbol;
    private Lexer lexer;   
    private Scanner scanner; 

   public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.scanner = new Scanner(System.in);
        try {
            this.symbol = lexer.nextToken();  
        } catch (IOException e) {
            throw new RuntimeException("Chyba pri načítavaní tokenu", e); 
        }
    }

    public int statement() {
        return expr();  
    }


    public int expr() {
        int result = term(); 

        while (symbol == Token.PLUS || symbol == Token.MINUS) {
            Token op = symbol;
            consume();  
            if (op == Token.PLUS) {
                result += term();  
            } else if (op == Token.MINUS) {
                result -= term(); 
            }
        }
        return result;
    }

    public int term() {
        int result = nul(); 

        while (symbol == Token.MULTIPLE || symbol == Token.DIVIDE) {
            Token op = symbol;
            consume();
            if (op == Token.MULTIPLE) {
                result *= nul(); 
            } else if (op == Token.DIVIDE) {
                result /= nul(); 
            }
        }
        return result;
    }

    public int nul() {
        int result = 0;

        if (symbol == Token.NUMBER) {
            result = lexer.getValue(); 
            consume(); 
        } else if (symbol == Token.VARIABLE) {
            String variableName = lexer.getVariableName(); 
            result = getVariableValue(variableName); 
            consume();  
        } else if (symbol == Token.LPAR) {
            consume();  
            result = expr(); 
            match(Token.RPAR);  
        } else {
            throw new CalculatorException("Neplatný token: " + symbol); 
        }

        return result;
    }

    private int getVariableValue(String variableName) {
        System.out.print("Zadaj hodnotu premennej " + variableName + ": ");
        return scanner.nextInt();  
    }

    public void match(Token expectedSymbol) {
        if (symbol == expectedSymbol) {
            consume(); 
        } else {
            throw new CalculatorException("Očakávaný token: " + expectedSymbol + ", ale dostali sme: " + symbol);
        }
    }

    public void consume() {
        try {
            symbol = lexer.nextToken();  
        } catch (IOException e) {
            throw new RuntimeException("Chyba pri načítavaní tokenu", e);
        }
    }
}