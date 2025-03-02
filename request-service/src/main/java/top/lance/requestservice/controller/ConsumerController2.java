package top.lance.requestservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: lance
 * @createTime: 2025/02/28 8:40
 * @description:
 **/
@RestController
public class ConsumerController2 {
    @Resource
    private RestTemplate restTemplate;
    private final String SERVICE_URL = "http://localhost:8086";
    @GetMapping("/restTemplateTest")
    public String restTemplateTest() {
        return restTemplate.getForObject(SERVICE_URL+"/hello", String.class);
    }
}
