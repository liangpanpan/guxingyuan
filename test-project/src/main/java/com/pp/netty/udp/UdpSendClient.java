package com.pp.netty.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/6       create this file
 * </pre>
 */
public class UdpSendClient {

    public static void main(String[] args) throws IOException {

        DatagramSocket socket = new DatagramSocket();
        try {
            InetSocketAddress address = new InetSocketAddress("localhost", 8088);

            byte[] requestData = "Hello".getBytes(StandardCharsets.UTF_8);

            socket.setSoTimeout(1000);
            DatagramPacket packet = new DatagramPacket(requestData, requestData.length, address);
            socket.send(packet);

            byte[] fromClient = new byte[1024];
            DatagramPacket frompacket = new DatagramPacket(fromClient, fromClient.length);//创建数据包，将会用数据包接受收到的信息

            socket.receive(frompacket);
            String responseMessage = new String(fromClient, 0, frompacket.getLength());
            System.out.println(responseMessage);
        } catch (Exception ex) {
            throw ex;
        } finally {
            socket.close();
        }
    }

}
