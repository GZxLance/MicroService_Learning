package top.lance.helloservice.service;

import org.springframework.stereotype.Service;

/**
 * @author： Lance
 * @create： 2025/2/28
 * @Description HelloService
 **/
@Service
public class HelloService {
    public String getName() {
        return "hello service";
    }

    public String sayHello(String name) {
        return "hello" + name;
    }
}
