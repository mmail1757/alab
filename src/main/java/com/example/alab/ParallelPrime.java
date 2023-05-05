package com.example.alab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelPrime {

    static final int THREADS = 2;
    final static SortedSet<Integer> set = Collections.synchronizedSortedSet(new TreeSet<>());

    final static AtomicInteger dig= new AtomicInteger(0);

    static int limit = 1_000_000;


    public static boolean isPrime(int n) {
        if (n == 2 || n == 3 || n == 5) return true;
        if (n <= 1 || (n&1) == 0) return false;

        for (int i = 3; i*i <= n; i += 2)
            if (n % i == 0) return false;

        return true;
    }

    public static void main(String[] args) throws Exception {
        Thread[] t = new Thread[THREADS];
        Map<Integer, BufferedWriter> writers = new ConcurrentHashMap<>();
        long begin = System.currentTimeMillis();
        BufferedWriter result = new BufferedWriter(new FileWriter("Result.txt", true));
        AtomicBoolean streamClosed = new AtomicBoolean(false);
        for (int i=0; i<THREADS; i++) {
            writers.put(i, new BufferedWriter(new FileWriter("Thread" +i + ".txt", true)));
            t[i] = new Thread(() -> {
                int value;
                while (true) {
                    value = dig.incrementAndGet();
                    if (value >= limit) {
                        try {
                            writers.values().forEach(e -> {
                                try {
                                    e.close();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                            if (value > limit) {
                                streamClosed.set(true);
                                writeResult(result);
                                result.close();
                            }

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    }
                    System.out.println("Calculating prime value in thread " + Thread.currentThread().getName());
                    if (isPrime(value)) {
                        System.out.println("Add prime value in thread " + Thread.currentThread().getName());
                        set.add(value);
                        synchronized (set) {
                            try {
                                if (set.size() % 16 == 0 || value == limit - 1) {
                                    writeResult(result);
                                }
                                writers.get(Integer.valueOf(Thread.currentThread().getName().split("-")[1]))
                                        .append(value + " ");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }

                }
            });
            t[i].start();
        }

        for (int i=0; i<THREADS; i++)
            t[i].join();
        System.out.println("Time to run: " + (System.currentTimeMillis() - begin));
    }

    private static void writeResult(BufferedWriter writer) {
        StringBuilder sb = new StringBuilder();
        try {
            set.forEach(item -> sb.append(item).append(" "));
            writer.append(String.valueOf(sb)).append(" ");
            sb.setLength(0);
            set.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

