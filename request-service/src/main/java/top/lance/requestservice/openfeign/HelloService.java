package top.lance.requestservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: lance
 * @createTime: 2025/02/28 10:11
 * @description:
 **/
@FeignClient(value = "hello-service" ,url = "http://localhost:8086")
public interface HelloService {
    @GetMapping(value = "sayHello")
    String hi(@RequestParam String name);
}
