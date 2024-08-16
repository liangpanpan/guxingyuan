package com.panpan;

import java.io.Serializable;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/4/9       create this file
 * </pre>
 */
public class AlarmTestBo implements Serializable {

    private String sendTime;

    /**
     * session会话ID
     */
    private String sessionId;

    /**
     * 威胁事件名称ID
     */
    private Integer threatEventId;

    /**
     * 威胁类型ID
     */
    private Integer threatTypeId;

    /**
     * 受影响IP
     */
    private String affectedIp;

    /**
     * 受影响端口
     */
    private Integer affectedPort;

    /**
     * 攻击者IP
     */
    private String attackIp;

    /**
     * 攻击者端口
     */
    private Integer attackPort;

    /**
     * 攻击状态
     * 0： 状态未知
     * 1： 攻击成功
     * 2： 攻击失败
     */
    private Integer attackStatus;

    /**
     * 方向
     * 0：出站
     * 1：入站
     */
    private Integer dataDirection;

    /**
     * 传输协议
     * TCP UDP
     */
    private String transferProtocols;

    /**
     * 应用协议 数字
     * 1  DNS
     * 2  HTTP
     * 3  HTTPS
     */
    private String applicationProtocols;

    /**
     * ioc的值
     */
    private String ioc;

    /**
     * IOC 类型
     * 0：IP
     * 1：域名
     * 2：URL
     * 3：Email
     */
    private Integer iocType;

    /**
     * 响应动作
     * 0：忽略
     * 1：告警
     * 2：阻断
     */
    private Integer actionState;

    /**
     * 告警来源 1系统默认
     */
    private Integer source;

    /**
     * 事件级别  1-高  2-中  3-低
     */
    private Integer eventLevel;


    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getThreatEventId() {
        return threatEventId;
    }

    public void setThreatEventId(Integer threatEventId) {
        this.threatEventId = threatEventId;
    }

    public Integer getThreatTypeId() {
        return threatTypeId;
    }

    public void setThreatTypeId(Integer threatTypeId) {
        this.threatTypeId = threatTypeId;
    }

    public String getAffectedIp() {
        return affectedIp;
    }

    public void setAffectedIp(String affectedIp) {
        this.affectedIp = affectedIp;
    }

    public Integer getAffectedPort() {
        return affectedPort;
    }

    public void setAffectedPort(Integer affectedPort) {
        this.affectedPort = affectedPort;
    }

    public String getAttackIp() {
        return attackIp;
    }

    public void setAttackIp(String attackIp) {
        this.attackIp = attackIp;
    }

    public Integer getAttackPort() {
        return attackPort;
    }

    public void setAttackPort(Integer attackPort) {
        this.attackPort = attackPort;
    }

    public Integer getAttackStatus() {
        return attackStatus;
    }

    public void setAttackStatus(Integer attackStatus) {
        this.attackStatus = attackStatus;
    }

    public Integer getDataDirection() {
        return dataDirection;
    }

    public void setDataDirection(Integer dataDirection) {
        this.dataDirection = dataDirection;
    }

    public String getTransferProtocols() {
        return transferProtocols;
    }

    public void setTransferProtocols(String transferProtocols) {
        this.transferProtocols = transferProtocols;
    }

    public String getApplicationProtocols() {
        return applicationProtocols;
    }

    public void setApplicationProtocols(String applicationProtocols) {
        this.applicationProtocols = applicationProtocols;
    }

    public String getIoc() {
        return ioc;
    }

    public void setIoc(String ioc) {
        this.ioc = ioc;
    }

    public Integer getIocType() {
        return iocType;
    }

    public void setIocType(Integer iocType) {
        this.iocType = iocType;
    }

    public Integer getActionState() {
        return actionState;
    }

    public void setActionState(Integer actionState) {
        this.actionState = actionState;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(Integer eventLevel) {
        this.eventLevel = eventLevel;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
