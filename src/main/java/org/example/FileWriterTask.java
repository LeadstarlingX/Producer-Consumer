package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

class FileWriterTask implements Runnable {
    private BlockingQueue<String> inputQueue;
    private String fileName;
    private int numberOfProcessors;

    public FileWriterTask(BlockingQueue<String> queue, String file, int totalProcessors) {
        this.inputQueue = queue;
        this.fileName = file;
        this.numberOfProcessors = totalProcessors;
    }

    @Override
    public void run() {
        int finishedProcessors = 0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            while (true) {
                String line = inputQueue.take();

                // Check for stop signal
                if (line == FileProcessingApp.POISON_PILL) {
                    finishedProcessors++;
                    // Only stop writing when ALL processors have sent their termination signal
                    if (finishedProcessors == numberOfProcessors) {
                        break;
                    }
                    continue; // Wait for other processors
                }

                // Write result to file
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Writer: Finished writing to file.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}