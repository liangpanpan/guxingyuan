package com.tjun.socket.receive;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/11/4       create this file
 * </pre>
 */
public class UdpThreadReceiveMessage {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 100, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10000));

    public static AtomicLong atomicLong = new AtomicLong(0);

    public static void main(String[] args) {

        int port = 11000;

        if (args.length > 0) {
            try {
                for (String arg : args) {
                    if (arg.startsWith("-Dport=")) {
                        String portStr = arg.substring("-Dport=".length());
                        port = Integer.valueOf(portStr);
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket(port);
            System.out.println("监听端口：" + port);

            // 创建一个用于存储接收数据的字节数组
            byte[] receiveData = new byte[1024];

            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("接收到的数据:" + atomicLong.get());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            thread.start();

            while (true) {
                // 创建接收数据报
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                // 接收数据报
                serverSocket.receive(receivePacket);
                atomicLong.addAndGet(1L);

                executor.execute(() -> MessageHandle.print(receivePacket));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            serverSocket.close();
        }

        System.out.println("finish Udp socket service!");
    }


}
