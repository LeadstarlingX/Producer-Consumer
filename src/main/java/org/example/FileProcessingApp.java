package org.example;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileProcessingApp {


    public static final String POISON_PILL = new String("POISON_PILL");

    public static void main(String[] args) {


        System.out.println("1. Generating input file...");
        RandomTextToFile.createInputFile("input.txt", 2000);


        BlockingQueue<String> queue1 = new LinkedBlockingQueue<>(100);
        BlockingQueue<String> queue2 = new LinkedBlockingQueue<>(100);

        int numberOfProcessors = 4;

        System.out.println("2. System started with " + numberOfProcessors + " consumers...");


        Thread producer = new Thread(new FileReaderTask(queue1, "input.txt", numberOfProcessors));
        producer.start();


        for (int i = 0; i < numberOfProcessors; i++) {
            new Thread(new ProcessorTask(queue1, queue2)).start();
        }
        
        // C. Writer
        Thread writer = new Thread(new FileWriterTask(queue2, "output.txt", numberOfProcessors));
        writer.start();

        try {
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("\n--- 3. Verification Results ---");
        verifyResults("input.txt", "output.txt");
    }

    private static void verifyResults(String inputFile, String outputFile) {
        try {
            long inputLines = Files.lines(Paths.get(inputFile)).count();
            long outputLines = Files.lines(Paths.get(outputFile)).count();

            System.out.println("Input Lines:  " + inputLines);
            System.out.println("Output Lines: " + outputLines);

            if (inputLines == outputLines) {
                System.out.println("SUCCESS: No data lost! Line counts match.");
            } else {
                System.err.println("FAILURE: Line counts mismatch! Check for race conditions.");
            }
        } catch (IOException e) {
            System.err.println("Verification failed: " + e.getMessage());
        }
    }
}