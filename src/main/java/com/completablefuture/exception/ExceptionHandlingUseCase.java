package com.completablefuture.exception;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExceptionHandlingUseCase {
    //update employee details to DB
    CompletableFuture<String> getEmployeeDatFuture() {
        return CompletableFuture.supplyAsync(() -> {
            graceFullyShutDown("Employee");
            return "Employee information update in DB";
        });/*.exceptionally(ex->{
            System.out.println("Unable to update employee information in DB");
            return "500 Intrenal Server Error";
        });*/

    }

    CompletableFuture<String> getEmployeeDocFuture() {
        return CompletableFuture.supplyAsync(() -> {
           // graceFullyShutDown("Employee");
            return "Employee Document update in DB";
        });/*.exceptionally(ex ->{
            System.out.println("Unable to update employee document in DB");
            return "500 Intrenal Server Error";
        });*/
    }

    //Update Employee Doc to DB


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExceptionHandlingUseCase exceptionHandlingUseCase = new ExceptionHandlingUseCase();
        CompletableFuture<String> combineFutureResult = exceptionHandlingUseCase.getEmployeeDatFuture().thenCombine(exceptionHandlingUseCase.getEmployeeDocFuture(), (result1, result2) -> {
            return result1 + " ---------------" + result2;
        })//Global Exception Handling
                .handle((res,ex)->{
            if(ex!=null){
                System.out.println("An error ocuured during procesing employee data into DB");
                return "Opertion failed";
            }
            return res;
        });
        System.out.println(combineFutureResult.get());
    }

    public static void graceFullyShutDown(String apiName){
        throw new RuntimeException(apiName+ "Service temporarily unaviailable ,please try again later" );
    }



}

