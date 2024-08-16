package com.tjun.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

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

            while (true) {
                // 创建接收数据报
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // 接收数据报
                serverSocket.receive(receivePacket);


                InetAddress address = receivePacket.getAddress();

                // 解析接收到的数据
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "utf-8");
                System.out.println("时间:" + sdf.format(new Date()) + ", 收到客户端:" + address.getHostAddress() + " 消息:" + message);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            serverSocket.close();
        }

        System.out.println("finish Udp socket service!");
    }
}