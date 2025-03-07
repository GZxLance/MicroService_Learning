package top.lance.userservice.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lance.userservice.config.CryProperties;


/**
 * @author WangL
 */
@RestController
//@RefreshScope
public class TestController {
//    @Value("${cry.username}")
//    private String username;
//
//    @Value("${cry.job}")
//    private String job;

    @Resource
    private CryProperties cryProperties;

    @GetMapping("/test")
    public String get(){
        return "读取到配置中心的值：" + cryProperties.getUsername() + "," + cryProperties.getJob();
    }

}