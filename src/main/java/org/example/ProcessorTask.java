package org.example;

import java.util.concurrent.BlockingQueue;

class ProcessorTask implements Runnable {
    private BlockingQueue<String> inputQueue;
    private BlockingQueue<String> outputQueue;

    public ProcessorTask(BlockingQueue<String> input, BlockingQueue<String> output) {
        this.inputQueue = input;
        this.outputQueue = output;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Take line from queue (waits if queue is empty)
                String line = inputQueue.take();

                // Check if it is the stop signal
                if (line.equals(FileProcessingApp.POISON_PILL)) {
                    // Signal the next queue that this specific thread is done
                    outputQueue.put(FileProcessingApp.POISON_PILL);
                    break; // Stop this thread
                }

                // --- PROCESSING LOGIC HERE ---
                // Example: Reversing the string and converting to Uppercase
                String processedLine = new StringBuilder(line).reverse().toString().toUpperCase();

                // Put processed result in next queue
                outputQueue.put(processedLine);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}