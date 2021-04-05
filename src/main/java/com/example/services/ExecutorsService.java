package com.example.services;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface ExecutorsService {
    <T> CompletableFuture<T> async(Supplier<T> task);
}
