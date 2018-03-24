package com.jacob.unsplash.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorHelper {
    private static final ExecutorService SERVICE = Executors.newCachedThreadPool();

    public static void submit(Runnable runnable) {
        SERVICE.submit(runnable);
    }
}
