package top.lance.requestservice.controller;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: lance
 * @createTime: 2025/02/28 8:57
 * @description:
 **/
@RestController
public class ConsumerController4 {
    @Resource
    private RestTemplate restTemplate;
    private final String SERVICE_URL = "https://www.wanandroid.com";

    // 使用 RestTemplate 访问接口（不带参数）
    @GetMapping("/restTemplate")
    public String restTemplateTest() {
        return restTemplate.getForObject(SERVICE_URL + "/harmony/index/json", String.class);
    }
}
