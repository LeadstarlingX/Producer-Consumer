package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;


class FileReaderTask implements Runnable {
    private final BlockingQueue<String> outputQueue;
    private final String fileName;
    private final int numberOfConsumers;

    public FileReaderTask(BlockingQueue<String> queue, String file, int consumers) {
        this.outputQueue = queue;
        this.fileName = file;
        this.numberOfConsumers = consumers;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                outputQueue.put(line);
            }

            for (int i = 0; i < numberOfConsumers; i++) {
                outputQueue.put(FileProcessingApp.POISON_PILL);
            }
            System.out.println("Producer: Finished reading file.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}