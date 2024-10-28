package com.completablefuture.aync;

import java.util.concurrent.CompletableFuture;

public class StockPriceDataFetcher {

    public CompletableFuture<Double> fetchStockPricesFromApi1(String symbol){
        return CompletableFuture.supplyAsync(()->{
            simulateDelay(2000);
            return 150.0;
        });
    }
    public CompletableFuture<Double> fetchStockPricesFromApi2(String symbol){
        return CompletableFuture.supplyAsync(()->{
            simulateDelay(1000);
            return 155.0;
        });
    }
    private void simulateDelay(int millis){
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        StockPriceDataFetcher fetcher = new StockPriceDataFetcher();
        String stockSymbol="APPL";
        //fetch stock price from both the APIS
        CompletableFuture<Double> api1Result =  fetcher.fetchStockPricesFromApi1(stockSymbol);
        CompletableFuture<Double> api2Result =  fetcher.fetchStockPricesFromApi2(stockSymbol);
        //Use anyOf to wait any of the future to complete
        CompletableFuture<Object> anyOfResults = CompletableFuture.anyOf(api1Result, api2Result);
        //Process the result
        anyOfResults.thenAccept(price->{
            System.out.println("Received stock price :$" + price);
        }).join();


    }
}
