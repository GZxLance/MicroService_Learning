package top.lance.productservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author： Lance
 * @create： 2025/2/21
 * @Description ProductController
 **/
@RestController
public class ProductController {

    @GetMapping("/product")
    public String getUser(@RequestParam String productId){
        return "商品信息:" + productId;
    }
}