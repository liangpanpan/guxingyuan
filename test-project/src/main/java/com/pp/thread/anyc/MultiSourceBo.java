package com.pp.thread.anyc;

import lombok.Builder;
import lombok.Data;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/7/10       create this file
 * </pre>
 */
@Data
@Builder
public class MultiSourceBo {

    private String id;

    private String name;

    private String source;

    private String target;

}
