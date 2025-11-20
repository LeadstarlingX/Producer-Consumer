package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileProcessingApp {

    public static final String POISON_PILL = new String("POISON_PILL");

    public static void main(String[] args) {

        System.out.println("Generating input file...");
        RandomTextToFile.createInputFile("input.txt", 2000);


        BlockingQueue<String> queue1 = new LinkedBlockingQueue<>(100);
        BlockingQueue<String> queue2 = new LinkedBlockingQueue<>(100);

        int numberOfProcessors = 4;

        System.out.println("System started...");


        Thread producer = new Thread(new FileReaderTask(queue1, "input.txt", numberOfProcessors));
        producer.start();


        for (int i = 0; i < numberOfProcessors; i++) {
            new Thread(new ProcessorTask(queue1, queue2)).start();
        }


        Thread writer = new Thread(new FileWriterTask(queue2, "output.txt", numberOfProcessors));
        writer.start();
    }
}