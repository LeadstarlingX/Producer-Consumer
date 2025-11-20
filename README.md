# Producer-Consumer File Processing System

## Overview

This project implements a multi-threaded **Producer–Consumer** pipeline in Java. It fulfills the **"First Choice" (الاختيار الأول)** requirement of the Thread Synchronization assignment.

The application simulates a concurrent file-processing workflow where data is read from an input file, processed by multiple consumer threads in parallel, and then written to an output file by a single writer thread. The architecture ensures thread safety and efficient data handling using blocking queues.

---

## Architecture

The system follows a structured data-flow pipeline:

1. **Producer (`FileReaderTask`)**  
   Reads lines from `input.txt` and inserts them into **Queue 1**.

2. **Processors (`ProcessorTask`)**  
   Multiple threads consume data from **Queue 1**, process it (reverse string + uppercase), and send output into **Queue 2**.

3. **Writer (`FileWriterTask`)**  
   Consumes messages from **Queue 2** and writes them to `output.txt`.

### Flow Diagram

Input File → Producer → Queue 1 → [Multiple Processors] → Queue 2 → Writer → Output File


---

## Features

- **Concurrent Processing** – Multiple threads process data in parallel.
- **Thread-Safe Queues** – Uses `LinkedBlockingQueue` to avoid manual synchronization.
- **Graceful Shutdown** – Implements a **Poison Pill** mechanism for clean termination.
- **Automatic Validation** – Compares line counts of input/output to ensure data integrity.
- **Random Input File Generation** – Automatically creates `input.txt` for testing.

---

## Prerequisites

- **Java JDK** 23 or higher
- **Maven** installed on system

---

## Project Structure

```text
src/main/java/org/example/
├── FileProcessingApp.java   # Main entry point
├── FileReaderTask.java      # Producer: Reads file → Queue 1
├── ProcessorTask.java       # Consumer: Queue 1 → Process → Queue 2
├── FileWriterTask.java      # Writer: Queue 2 → output.txt
└── RandomTextToFile.java    # Generates random input file
```


## Expected Output

1. Generating input file...
   Generated 2000 words and saved to 'input.txt'
2. System started with 4 consumers...
   ...
   Producer: Finished reading file.
   Thread-1 processed: ...
   Writer: Finished writing to file.

--- 3. Verification Results ---
Input Lines:  200
Output Lines: 200
SUCCESS: No data lost! Line counts match.
