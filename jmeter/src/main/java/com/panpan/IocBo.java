package com.panpan;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/4/10       create this file
 * </pre>
 */
public class IocBo {

    private String iocValue;

    /**
     * IOC 类型
     * 0：IP
     * 1：域名
     * 2：URL
     * 3：Email
     */
    private Integer iocType;

    public String getIocValue() {
        return iocValue;
    }

    public void setIocValue(String iocValue) {
        this.iocValue = iocValue;
    }

    public Integer getIocType() {
        return iocType;
    }

    public void setIocType(Integer iocType) {
        this.iocType = iocType;
    }
}
