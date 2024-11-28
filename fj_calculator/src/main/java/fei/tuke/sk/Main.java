package fei.tuke.sk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Zadajte matematický výraz:");

            String input = reader.readLine(); 

            Lexer lexer = new Lexer(input); 
            Parser parser = new Parser(lexer);

            int result = parser.statement();
            System.out.println("Výsledok: " + result);

        } catch (IOException e) {
            System.out.println("Chyba pri čítaní vstupu.");
        } catch (CalculatorException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
    }
}

