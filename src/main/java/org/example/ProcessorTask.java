package org.example;

import java.util.concurrent.BlockingQueue;

class ProcessorTask implements Runnable {
    private final BlockingQueue<String> inputQueue;
    private final BlockingQueue<String> outputQueue;

    public ProcessorTask(BlockingQueue<String> input, BlockingQueue<String> output) {
        this.inputQueue = input;
        this.outputQueue = output;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = inputQueue.take();

                if (line.equals(FileProcessingApp.POISON_PILL)) {
                    outputQueue.put(FileProcessingApp.POISON_PILL);
                    break;
                }

                String processedLine = new StringBuilder(line).reverse().toString().toUpperCase();

                outputQueue.put(processedLine);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}