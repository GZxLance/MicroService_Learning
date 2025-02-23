package top.lance.userservice.controller;

import io.micrometer.core.ipc.http.HttpSender;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author： Lance
 * @create： 2025/2/21
 * @Description UserController
 **/
//@RestController
//public class UserController {
//
//    @GetMapping("/user")
//    public String getUser(@RequestParam String username) {
//
//        return "User: " + username;
//    }
//}
@RestController
public class UserController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/user")
    public String getUser(@RequestParam String username,@RequestParam String question) {
        String aiServiceUrl = "http://localhost:8084/ask?content=" + question;
        String answerInfo = restTemplate.getForObject(aiServiceUrl, String.class);
        return "用户：" + username + "提问:" + question + "\n" + "AI回复" + answerInfo;
    }

}