package org.example;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileProcessingApp{

    // The "Poison Pill" is a special object reference to signal threads to stop
    public static final String POISON_PILL = new String("POISON_PILL");

    public static void main(String[] args) {
        // Queue 1: Between Reader and Processors
        BlockingQueue<String> queue1 = new LinkedBlockingQueue<>(100);
        // Queue 2: Between Processors and Writer
        BlockingQueue<String> queue2 = new LinkedBlockingQueue<>(100);

        int numberOfProcessors = 4; // Example: 4 threads processing data

        // 1. Start the Producer (Reader)
        Thread producer = new Thread(new FileReaderTask(queue1, "input.txt", numberOfProcessors));
        producer.start();

        // 2. Start Multiple Consumers (Processors)
        for (int i = 0; i < numberOfProcessors; i++) {
            new Thread(new ProcessorTask(queue1, queue2)).start();
        }

        // 3. Start the Final Consumer (Writer)
        Thread writer = new Thread(new FileWriterTask(queue2, "output.txt", numberOfProcessors));
        writer.start();

        System.out.println("System started...");
    }
}