package com.completablefuture.aync;

import java.util.concurrent.CompletableFuture;

public class MultipleApiDataFetcher {
    public CompletableFuture<String> fetchWeatherData(){
        return CompletableFuture.supplyAsync(()->{
            simulateDelay(2000);
            return "Weather : Sunny,25'C";
        });
    }

    public CompletableFuture<String> fetchNewsHeadLines(){
        return CompletableFuture.supplyAsync(()->{
            simulateDelay(3000);
            return "New Java 23 Released";
        });
    }

    public CompletableFuture<String> fetchStockPrices(){
        return CompletableFuture.supplyAsync(()->{
            simulateDelay(1500);
            return "Stocks : AAPL - $150 ,GOOGL-$2800";
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
        MultipleApiDataFetcher multipleApiDataFetcher = new MultipleApiDataFetcher();
        //Combine multiple independent future (more than 2) -> allOf(n task)
        //weatherDetailApi
        //news apis
        //stockprice apis
        //Combine multiple independent future (more than 2) -> allOf(n task)
        //WeatherDetailsAPI
        CompletableFuture<String> weatherFuture = multipleApiDataFetcher.fetchWeatherData();
        //new ApI
        CompletableFuture<String> newFuture = multipleApiDataFetcher.fetchNewsHeadLines();
        //stockPrice Apis
        CompletableFuture<String> stockPriceFuture = multipleApiDataFetcher.fetchStockPrices();
        //wait for all future to complete
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(weatherFuture, newFuture, stockPriceFuture);
        //process results after all future are completed
        allFuture.thenRun(()->{
            String weather = weatherFuture.join();
            String news = newFuture.join();
            String stock = stockPriceFuture.join();
            System.out.println("Aggregated Data :");
            System.out.println(weather);
            System.out.println(news);
            System.out.println(stock);
        }).join();

    }

}

