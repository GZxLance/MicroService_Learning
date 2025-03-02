package top.lance.requestservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.lance.requestservice.vo.User;
import top.lance.requestservice.vo.UserVO;

import java.util.List;

/**
 * @author: lance
 * @createTime: 2025/02/28 15:13
 * @description:
 **/
@FeignClient(value = "node-service",url = "http://localhost:3000")
public interface NodeService {
    @PostMapping("/users")
    User createUser(@RequestBody User user);

    @PostMapping("/updateUser")
    UserVO updateUser(@RequestBody UserVO user);

    @DeleteMapping("/users/{id}")
    UserVO deleteUser(@PathVariable("id") Integer id);

    @GetMapping("/users")
    List<User> getUsers();
}
