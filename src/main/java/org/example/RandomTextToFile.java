package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomTextToFile {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";


    private static String randomWord(Random rand) {
        int wordLength = rand.nextInt(8) + 3;
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < wordLength; i++) {
            char c = ALPHABET.charAt(rand.nextInt(ALPHABET.length()));
            word.append(c);
        }
        return word.toString();
    }


    public static void createInputFile(String filename, int wordCount) {
        Random rand = new Random();
        StringBuilder text = new StringBuilder();

        System.out.println("--- Generator Started ---");

        for (int i = 1; i <= wordCount; i++) {
            text.append(randomWord(rand)).append(" ");

            if (i % 10 == 0) {
                text.append("\n");
            }
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(text.toString());
            System.out.println("Generated " + wordCount + " words and saved to '" + filename + "'");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("--- Generator Finished ---\n");
    }

}