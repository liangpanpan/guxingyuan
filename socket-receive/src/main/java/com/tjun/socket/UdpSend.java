package com.tjun.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/5/14       create this file
 * </pre>
 */
public class UdpSend {

    public static void main(String[] args) {
        try {
            // 创建UDP套接字
            DatagramSocket clientSocket = new DatagramSocket();

            // 创建要发送的数据
            String message = "Hello, UDP123!";
            byte[] sendData = message.getBytes();

            // 指定服务器地址和端口
            String serviceIp = "192.168.30.249";
            int serverPort = 10514;
            InetAddress serverAddress = InetAddress.getByName(serviceIp);


            // 创建数据报
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

            // 发送数据报
            clientSocket.send(sendPacket);

            // 关闭UDP套接字
            clientSocket.close();

            System.out.println("finish udp send.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
