package top.lance.requestservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.lance.requestservice.openfeign.HelloService;

/**
 * @author: lance
 * @createTime: 2025/02/28 10:18
 * @description:
 **/
@RestController
public class ConsumerController6 {
    @Resource
    private HelloService helloService;

    @GetMapping("/hi")
    public String sayHi(@RequestParam String name){
        return helloService.hi(name);
    }
}

