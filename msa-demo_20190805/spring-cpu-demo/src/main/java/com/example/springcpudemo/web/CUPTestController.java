/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2019 TenxCloud. All Rights Reserved.
 *
 * 2019/7/13 @author xinjie
 */
package com.example.springcpudemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Random;

/**
 * @ClassName CUPTestController
 * @Description TODO
 * @Author xinjie
 * @CreateDate 2019/7/13 11:04
 */
@RestController
public class CUPTestController {

    @GetMapping(value = "/cpu")
    @ResponseBody
    public CPUTestThread cpuTestThread() {
        CPUTestThread cpuTestThread = new CPUTestThread();
        for (int i = 0; i < 8; i++) {
            Thread cpuTest = new Thread(cpuTestThread);
            cpuTest.start();
        }

        //Windows Task Manager shows
        // try {
        //     Runtime.getRuntime().exec("taskmgr");
        // } catch (IOException e1) {
        //     e1.printStackTrace();
        // }
        return cpuTestThread;
    }
    @GetMapping(value = "/memoryTest")
    @ResponseBody
    public void memoryTest() {
        int len = 1024 * 1024 * 1024;
        Random rnd = new Random(0);
        long result = 0;

        System.out.println("Declaring...");
        long t1 = System.currentTimeMillis();
        short[] its = new short[len];

        System.out.println("Randomizing...");
        long t2 = System.currentTimeMillis();
        for (int i = 0; i < len; i++)
            its[i] = (short)rnd.nextInt();

        System.out.println("Reading...");
        long t3 = System.currentTimeMillis();
        for (int i = 0; i < len; i++)
            result += its[i];
        long t4 = System.currentTimeMillis();

        System.out.println("Result: " + result);
        System.out.println("Declaration: " + (t2 - t1));
        System.out.println("Randomization: " + (t3 - t2));
        System.out.println("Reading: " + (t4 - t3));
    }

    class CPUTestThread implements Runnable {

        @Override
        public void run() {
            long t = System.currentTimeMillis();
            long total = 0;
            int busyTime = 10;
            int idleTime = busyTime;
            long startTime = 0;
            while (System.currentTimeMillis() < t + 300000 ) {
                startTime = System.currentTimeMillis();
                System.out.println(System.currentTimeMillis()+","+startTime+","+(System.currentTimeMillis() - startTime));
                total++;
                // busy loop
                while ((System.currentTimeMillis() - startTime) <= busyTime)
                    ;
                // idle loop
                try {
                    Thread.sleep(idleTime);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            System.out.println("The thread " + Thread.currentThread().getName() + " has done " + total + " loops in 300s.");
        }

    }

}
