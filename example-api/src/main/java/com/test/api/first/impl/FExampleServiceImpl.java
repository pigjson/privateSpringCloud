package com.test.api.first.impl;

import com.test.api.first.FExampleService;
import org.springframework.stereotype.Component;

/**
 * 测试example
 */
@Component
public class FExampleServiceImpl implements FExampleService {
    @Override
    public Object queryExample() {
        return "出错了";
    }
}
