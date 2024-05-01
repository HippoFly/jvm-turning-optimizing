package com.example.jvmturningoptimizing.OOM;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class OOM_Storage_Leakage {
    private static class CardInfo {
        BigDecimal price = new BigDecimal(0.0);
        String name = "Jack";
        int age = 15;
        Date birthDate = new Date();

        public void m() {
            LocalTime currentTime = LocalTime.now();

            System.out.println("当前时间（精确到秒）: " + currentTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss SSS")));
        }

    }

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(50,
            new ThreadPoolExecutor.DiscardOldestPolicy());


    public static void main(String[] args) throws InterruptedException {
        executor.setMaximumPoolSize(50);
        for (; ; ) {
            modelFit();
            Thread.sleep(100);
        }
    }

    private static void modelFit() {
        List<CardInfo> list = getAllCardInfo();
        list.forEach(cardInfo -> {
            executor.scheduleWithFixedDelay(() ->
            {
                cardInfo.m();

            }, 2, 3, TimeUnit.SECONDS);
        });
    }

    private static List<CardInfo> getAllCardInfo() {
        List<CardInfo> taskList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CardInfo ci = new CardInfo();
            taskList.add(ci);

        }
        return taskList;
    }
}

