package com.test.api.first;

import com.test.api.first.impl.FExampleServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "example-service", path = "", fallback = FExampleServiceImpl.class)
public interface FExampleService {
    /**
     * 参数可以传bean
     *
     * @return
     */
    @RequestMapping(value = "/exampleController/testPost", method = RequestMethod.POST)
    Object queryExample();
}
