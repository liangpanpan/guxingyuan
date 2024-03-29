package com.gc.dgmodel.state;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/18       create this file
 * </pre>
 */
public class ConcreteState1 extends State {
    @Override
    public void handle1() {
        //本状态下必须处理的逻辑
    }

    @Override
    public void handle2() {
        //设置当前状态为stat2
        super.context.setCurrentState(Context.STATE2);
        //过渡到state2状态，由Context实现
        super.context.handle2();
    }
}
