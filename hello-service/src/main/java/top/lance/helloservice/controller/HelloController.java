package top.lance.helloservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.lance.helloservice.service.HelloService;

/**
 * @author： Lance
 * @create： 2025/2/28
 * @Description HelloController
 **/
@RestController
public class HelloController {
    @Resource
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return "hello from " + helloService.getName();
    }

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return helloService.sayHello(name);
    }
}
