package com.gc.dgmodel.memento;

/**
 * 备忘录角色
 * 负责存储Originator发起人对象的内部状态，在需要的时候提供发起人需要的内部状态。
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/18       create this file
 * </pre>
 */
public class Memento {
    //发起人的内部状态
    private String state = "";

    //构造函数传递参数
    public Memento(String _state) {
        this.state = _state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
