package com.example.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Service
public class ExecutorsServiceImpl implements ExecutorsService {
    private static ExecutorService executorService;

    public ExecutorsServiceImpl(@Value("${app.threads}") int countThread) {
        executorService = Executors.newFixedThreadPool(countThread);
    }

    @Override
    public <T> CompletableFuture<T> async(Supplier<T> task){
        return CompletableFuture.supplyAsync(task, executorService);
    }
}

