package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;


class FileReaderTask implements Runnable {
    private BlockingQueue<String> outputQueue;
    private String fileName;
    private int numberOfConsumers;

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
                // Put line in queue (waits if queue is full)
                outputQueue.put(line);
            }

            // Finished reading. Send a Poison Pill for EACH processor thread


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}