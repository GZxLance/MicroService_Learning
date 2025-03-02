package top.lance.requestservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author: lance
 * @createTime: 2025/02/28 8:59
 * @description:
 **/
@RestController
public class ConsumerController5 {
    private final String SERVICE_URL = "https://www.wanandroid.com";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(SERVICE_URL)
            .build();


    @GetMapping("/webClient")
    public Mono<String> webClientTest(String id) {
        // 使用 WebClient 发起异步请求
        return webClient
                .get()
                .uri("/article/list/"+id+"/json")
                .retrieve()
                .bodyToMono(String.class);
    }
}
