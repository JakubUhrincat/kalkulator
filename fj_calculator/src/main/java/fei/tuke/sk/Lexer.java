package fei.tuke.sk;


import java.io.IOException;

public class Lexer {
    private int current;
    private int value;
    private String input;
    private int position;
    private String variableName;


    public Lexer(String input) {
        this.input = input;
        this.position = 0;
        if (input.length() > 0) {
            this.current = input.charAt(position);
        }
    }

    public Token nextToken() throws IOException {
        while (Character.isWhitespace(current)) {
            consume();
        }

        if (position >= input.length()) {
            return Token.EOF; 
        }

        if (Character.isDigit(current)) {
            value = 0;
            while (Character.isDigit(current)) {
                value = value * 10 + (current - '0');
                consume();
            }
            return Token.NUMBER;
        }

        if (Character.isLetter(current)) {
            StringBuilder variable = new StringBuilder();
            while (Character.isLetter(current)) {
                variable.append((char) current);
                consume();
            }
            variableName = variable.toString();
            return Token.VARIABLE;
        }

        if (current == '+') {consume(); return Token.PLUS;}

        if (current == '-') {consume(); return Token.MINUS;}

        if (current == '*') {consume(); return Token.MULTIPLE;}

        if (current == '/') {consume(); return Token.DIVIDE;}

        if (current == '(') {consume(); return Token.LPAR;}

        if (current == ')') {consume(); return Token.RPAR;}

        throw new IOException("Neznámy znak: " + (char) current);
    }

    public void consume() {
        position++;
        if (position < input.length()) {
            current = input.charAt(position);
        } else {
            current = -1; 
        }
    }

    public int getValue() {
        return value;
    }

    public String getVariableName() {
        return variableName;
    }
}
