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

import java.io.*;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName MapperIO
 * @Description TODO
 * @Author xinjie
 * @CreateDate 2019/7/13 11:59
 */
@RestController
public class MapperIO {
    private static int numOfInts = 200000;  // 读写次数

    private abstract static class Tester {
        private String name;
        public Tester(String name) {
            this.name = name;
        }

        public void runTest() {
            System.out.print(name + ": ");
            try {
                long start = System.nanoTime();
                test();
                double duration = System.nanoTime() - start;
                System.out.format("%.5f\n", duration/1.0e9);  // 由2f改为5f
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public abstract void test() throws IOException;
    }

    private static  Tester[] tests = {
            new Tester("Stream Write") {
                @Override
                public void test() throws IOException {
                    DataOutputStream dos = new DataOutputStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(new File("temp.tmp"))));

                    for (int i = 0; i < numOfInts; i++) {
                        dos.writeInt(i);
                    }
                    dos.close();
                }
            }, new Tester("Mapped Write") {
        @Override
        public void test() throws IOException {
            FileChannel fc =
                    new RandomAccessFile("temp.tmp", "rw").getChannel();
            IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
            for (int i = 0; i < numOfInts; i++) {
                ib.put(i);
            }
            fc.close();
        }
    },
            new Tester("Stream Read") {
                @Override
                public void test() throws IOException {
                    DataInputStream dis = new DataInputStream(
                            new BufferedInputStream(new FileInputStream("temp.tmp")));
                    for (int i = 0; i < numOfInts; i++) {
                        dis.readInt();
                    }
                    dis.close();
                }
            }, new Tester("Mapped Read") {
        @Override
        public void test() throws IOException {
            FileChannel fc =
                    new RandomAccessFile("temp.tmp", "rw").getChannel();
            IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
            while (ib.hasRemaining()) {
                ib.get();
            }
            fc.close();
        }
    }, new Tester("Stream Read/Write") {
        @Override
        public void test() throws IOException {
            RandomAccessFile fc =
                    new RandomAccessFile("temp.tmp", "rw");
            fc.writeInt('a');
            for (int i = 0; i < numOfInts; i++) {
                fc.seek(i * 4);
                fc.writeInt(fc.readInt());
            }
            fc.close();
        }
    },
            new Tester("Mapped Read/Write") {
                @Override
                public void test() throws IOException {
                    FileChannel fc =
                            new RandomAccessFile("temp.tmp", "rw").getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                    ib.put('b');
                    for (int i = 1; i < numOfInts; i++) {
                        ib.put(ib.get(i - 1));
                    }
                    fc.close();
                }
            }
    };
    @GetMapping(value = "/diskTest")
    @ResponseBody
    public void diskTest() {
        for (Tester test : tests) {
            test.runTest();
        }
    }
}
