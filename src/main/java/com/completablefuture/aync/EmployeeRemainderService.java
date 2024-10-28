package com.completablefuture.aync;

import com.completablefuture.database.EmployeeDataBase;
import com.completablefuture.dto.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeRemainderService {
    Executor executor = Executors.newFixedThreadPool(5);
    public   CompletableFuture<Void> sendRemainderToEmployee() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> voidCompletableFutre = CompletableFuture.supplyAsync(() -> {
            System.out.println("FetchEmployee ::" + Thread.currentThread().getName());
            return EmployeeDataBase.fetchEmployee();
        },executor).thenApplyAsync((employees) -> {
            System.out.println("filter New Joiner Employee ::" + Thread.currentThread().getName());
            return employees.stream().filter(employee -> "TRUE".equals(employee.getNewJoiner())).collect(Collectors.toList());
        },executor).thenApplyAsync((employees) -> {
            System.out.println("filter training not completed for Employees ::" + Thread.currentThread().getName());
            return employees.stream().filter(employee -> "TRUE".equals(employee.getLearningPending())).collect(Collectors.toList());
        },executor).thenApplyAsync((employees) -> {
            System.out.println("Get Employee Id ::" + Thread.currentThread().getName());
            return employees.stream().map(Employee::getEmail).collect(Collectors.toList());
        },executor).thenAcceptAsync((email)->{
            System.out.println("Email Notification ::" + Thread.currentThread().getName());
            email.forEach(EmployeeRemainderService::sendEmail);
        },executor);
        return voidCompletableFutre;
    }

    public static void sendEmail(String email){
        System.out.println("Sending training remainder email to :"+email);
    }


    public static void main(String[] args) {
        EmployeeRemainderService employeeRemainderService = new EmployeeRemainderService();
        try {
            employeeRemainderService.sendRemainderToEmployee().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


    }
}
