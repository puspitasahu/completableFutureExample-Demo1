package com.completablefuture.aync;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

import com.completablefuture.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RunAsyncDemo {

    public Void saveEmployees(File jsonFile) throws ExecutionException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> runSyncFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Employee> employees =mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                    });
                    System.out.println("Thread ::" + Thread.currentThread().getName());
                    System.out.println(employees.size());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return runSyncFuture.get();
    }

    public Void saveEmployeesWithCustomExecutor(File jsonFile) throws ExecutionException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> runSyncFuture = CompletableFuture.runAsync(()->{
                try {
                    List<Employee> employees =mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                    });
                    System.out.println("Thread ::" + Thread.currentThread().getName());
                    System.out.println(employees.size());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            },executor
        );
        return runSyncFuture.get();
    }

    public static void main(String args[]) throws ExecutionException, InterruptedException {

        RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
        runAsyncDemo.saveEmployees(new File("employees.json"));
        runAsyncDemo.saveEmployeesWithCustomExecutor(new File("employees.json"));

    }
}
