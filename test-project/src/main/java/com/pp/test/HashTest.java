package com.pp.test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/12/25       create this file
 * </pre>
 */
public class HashTest {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            UUID uuid = UUID.randomUUID();
            System.out.println(uuid);
            String replace = uuid.toString().replace("-", "");
            long value = hashNum(replace);
            System.out.println(value);
            System.out.println(Math.abs(value) % 6);
        }

    }

    private static long hashNum(String uuid) {
        CRC32 crc32 = new CRC32();
        crc32.update(uuid.getBytes(StandardCharsets.UTF_8));
        return crc32.getValue();
    }


}
