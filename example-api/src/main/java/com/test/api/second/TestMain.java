package com.test.api.second;

import java.util.function.Consumer;

public class TestMain {

    public void happy(double money, Consumer<Double> con){
        con.accept(money);
    }

    public static void main(String[] args) {
        TestMain t = new TestMain();
        t.happy(10000, (m) -> System.out.println("你们刚哥喜欢大宝剑，每次消费：" + m + "元"));
    }
}
