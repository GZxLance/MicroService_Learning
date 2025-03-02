package top.lance.userservice.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.lance.userservice.model.entity.User;
import top.lance.userservice.service.UserService;

/**
 * @author: lance
 * @projectName: user-service
 * @description:
 */
@RestController
@Slf4j
public class UserController {
//    @Resource
//    private RestTemplate restTemplate;

//    @GetMapping("/user")
//    public String getUser(@RequestParam String username) {
//        return "User: " + username;
//    }

//    @GetMapping("/user")
//    public String getUser(@RequestParam String username, @RequestParam String prompt) {
//        String aiServiceUrl = "http://localhost:8084/ai?prompt=" + prompt;
//        String nodejsServiceUrl = "http://localhost:8085/";
//        String pythonServiceUrl = "http://localhost:8086/hello";
//        String aiInfo = restTemplate.getForObject(aiServiceUrl, String.class);
//        String nodejsInfo = restTemplate.getForObject(nodejsServiceUrl, String.class);
//        String pythonInfo = restTemplate.getForObject(pythonServiceUrl, String.class);
//        return "User: " + username + ", AI回答: " + aiInfo + "NodeJS: " + nodejsInfo + "python: " + pythonInfo;
//    }

    @Resource
    private UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        log.info("用户服务被调用");
        return userService.getById(id);
    }

}
