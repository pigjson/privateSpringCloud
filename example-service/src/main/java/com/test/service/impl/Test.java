package com.test.service.impl;

import com.test.service.MyInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName Test
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/23
 **/
public class Test {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }

//        List<String> results = list.stream()
//                .filter(t->!"2".equals(t))
//                .limit(10)
//                .skip(1)
//                .distinct()
//                .collect(Collectors.toList());
//
//        results.stream()
//                .forEach(t-> System.out.println(t));


//        boolean res = list.stream()
//        .filter(t->!"2".equals(t))
//        .limit(10)
//        .skip(1)
//        .distinct()
//        .allMatch(t->Integer.parseInt(t)>1);
//        System.out.println(res);


//        int max = list.stream()
//                .map(Integer::new)
//                .reduce(0,
//                Integer::max);
//        System.out.println(max);

        List<Integer> lNameInCaps = list.stream()
                .map(Integer::new)
                .collect(Collectors.toList());


        lNameInCaps.stream().forEach(s -> System.out.println(s));


    }
}
