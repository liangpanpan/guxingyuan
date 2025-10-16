package com.gc.dgmodel.flyweight;

import java.util.HashMap;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/20       create this file
 * </pre>
 */
public class FlyweightFactory {
    //定义一个池容器
    private static final HashMap<String, Flyweight> pool = new HashMap<>();

    //享元工厂
    public static Flyweight getFlyweight(String extrinsic) {
        //需要返回的对象
        Flyweight flyweight;
        //在池中没有该对象
        if (pool.containsKey(extrinsic)) {
            flyweight = pool.get(extrinsic);
        } else {
            //根据外部状态创建享元对象
            flyweight = new ConcreteFlyweight1(extrinsic);
            //放置到池中
            pool.put(extrinsic, flyweight);
        }
        return flyweight;
    }

}
