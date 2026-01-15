package com.pp.leon;

import java.nio.ByteBuffer;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/11/4       create this file
 * </pre>
 */
public class LeonByteMessage {


    public static void main(String[] args) {

        // 前2位是int类型 描述消息类型
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.putInt(1);

        // 后两位是int类型 描述消息长度
        byteBuffer.putInt(4);

        // sessionid 32位

        // sip 4位

        // dip 4位

        // 源端口 2位

        // 目的端口 2位

        // 开始时间startTime 4 * 1000

        // 请求流量4

        // 相应流量 4

        // 协议 1

        // 应用协议 1

        // mac 地址








        byte[] array = byteBuffer.array();



        byte[] bs = new byte[4];
        System.arraycopy(array, 2, bs, 2, 2);
        ByteBuffer byteBuffer1 = ByteBuffer.wrap(bs);
        System.out.println(byteBuffer1.getInt());
    }
}
