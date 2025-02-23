package top.lance.orderservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author WangL
 */

//@RestController
//public class OrderController {
//    @Resource
//    private RestTemplate restTemplate;
//
//    @GetMapping("/order")
//    public String creatorOrder(@RequestParam String username,@RequestParam Integer productId){
//        String userServiceUrl = "http://localhost:8081/user?username=" + username;
//        String userInfo = restTemplate.getForObject(userServiceUrl, String.class);
//
//        String productUrl = "http://localhost:8083/product?productId=" + productId;
//        String productMes = restTemplate.getForObject(productUrl, String.class);
//
//        return "订单创建者是："  + "订单编号：" + productMes + "--" + userInfo;
//    }
//}

@RestController
public class OrderController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/order")
    public String creatorOrder() {
        String userServiceUrl = "http://localhost:3000";
        String userInfo = restTemplate.getForObject(userServiceUrl, String.class);


        return userInfo;
    }
}