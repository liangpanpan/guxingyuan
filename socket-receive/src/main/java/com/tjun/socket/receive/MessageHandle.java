package com.tjun.socket.receive;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/11/4       create this file
 * </pre>
 */
public class MessageHandle {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void print(DatagramPacket receivePacket) {
        InetAddress address = receivePacket.getAddress();
        // 解析接收到的数据
        String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);

        String format = "时间：%s, 收到客户端:%s 消息:%s";

        System.out.println(String.format(format, sdf.format(new Date()), address.getHostAddress(), message));
    }

}
