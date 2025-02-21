package top.lance.userservice.controller;

import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author： Lance
 * @create： 2025/2/21
 * @Description UserController
 **/
@RestController
public class UserController {

    @GetMapping("/user")
    public String getUser(@RequestParam String username) {

        return "User: " + username;
    }
}
