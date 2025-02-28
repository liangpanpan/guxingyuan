package com.pp.threadlocal;

/**
 * <pre>
 * @Version 1.0
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2020/7/2       create this file
 * </pre>
 */
public class ThreadLocalCounter {

    private Number number = new Number();

    private ThreadLocal<Number> num = new ThreadLocal<Number>() {
        @Override
        protected Number initialValue() {
            return new Number();
        }
    };


    public Number getNextNum() {

        Number n = num.get();

//        System.out.println("test1");

        n.add();

        num.set(n);
        return num.get();
    }

    public void setNumber() {
        this.num.set(this.number);
    }

    public static void main(String[] args) {
        ThreadLocalCounter tn = new ThreadLocalCounter();
        tn.setNumber();

        Counter counter1 = new Counter(tn);
        Counter counter2 = new Counter(tn);
        Counter counter3 = new Counter(tn);

        counter1.start();
        counter2.start();
        counter3.start();

    }

    static class Counter extends Thread {
        private ThreadLocalCounter tn;

        public Counter(ThreadLocalCounter tn) {
            this.tn = tn;
        }

        public void run() {
            for (int i = 0; i < 3; i++) {

                Number n = tn.getNextNum();

                System.out.println("thread-" + Thread.currentThread().getName() + "-" + n.getValue() + n.toString());
            }
        }


    }

}
