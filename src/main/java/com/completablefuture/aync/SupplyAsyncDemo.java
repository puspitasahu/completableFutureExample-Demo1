package com.completablefuture.aync;

import com.completablefuture.database.EmployeeDataBase;
import com.completablefuture.dto.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SupplyAsyncDemo {

    Executor executor = Executors.newCachedThreadPool();

    public List<Employee> getEmployee() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Employee>> listCompletableFuture =CompletableFuture.supplyAsync(()->{
            System.out.println("Executed by Thread ::" + Thread.currentThread().getName());
            return EmployeeDataBase.fetchEmployee();
        },executor);

        return listCompletableFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
        List<Employee> employees = supplyAsyncDemo.getEmployee();
        employees.stream().forEach(System.out::println);

    }
}
