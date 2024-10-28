package com.completablefuture.aync;

import com.completablefuture.database.EmployeeDataBase;
import com.completablefuture.dto.Employee;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CombineCompleteableFutureDemo {
    //combine dependent future(2)
    //Employee->rating
    public CompletableFuture<Employee> getEmployeeDetails(){
        return CompletableFuture.supplyAsync(()-> {
            System.out.println("getEmployeeDetails {}" + Thread.currentThread().getName());
            return EmployeeDataBase.fetchEmployee()
                    .stream()
                    .filter(emp -> "73-274-6476".equals(emp.getEmployeeId()))
                    .findAny()
                    .orElse(null);
        });
    }
    public CompletableFuture<Integer> getRatings(Employee employee) {
        return CompletableFuture.supplyAsync(()->{
            System.out.println("getRatings {}" + Thread.currentThread().getName());
            return employee.getRating();
        });
    }

    public CompletableFuture<Map<String,Long>> getEmployeeGenderCount(){
        return CompletableFuture.supplyAsync(()->{
            return EmployeeDataBase.fetchEmployee()
                    .stream()
                    .collect(Collectors.groupingBy(Employee::getGender,Collectors.counting()));
        });
    }

    public CompletableFuture<List<String>> getEmployeeEMails(){
        return CompletableFuture.supplyAsync(()->{
            return EmployeeDataBase.fetchEmployee()
                    .stream()
                    .map(Employee::getEmail)
                    .collect(Collectors.toList());
        });
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //combine dependent futures (2) -> then Compose
        CombineCompleteableFutureDemo cf = new CombineCompleteableFutureDemo();
        CompletableFuture<Integer> ratingResult = cf.getEmployeeDetails().thenComposeAsync(cf::getRatings);
        System.out.println("Rating ::" +  ratingResult.get());

        //combine independent futures (2) - thenCombine
        CompletableFuture<String> thenCombineResults =
                cf.getEmployeeGenderCount().thenCombineAsync(cf.getEmployeeEMails(), (empMap, emails) -> empMap + " " + emails);
        System.out.println("EmployeeDetails with Emails ::" + thenCombineResults.get());

        //Combine multiple independent future (more than 2) -> allOf(n task)
        //weatherDetailApi
        //news apis
        //stockprice apis


        //Combine multiple independent future (more than 2) -> anyOf(n task) (no need to wait for all)
        //Location -> X Y -> result
        //Stock Price -> X , Y, Z ->


    }


}
