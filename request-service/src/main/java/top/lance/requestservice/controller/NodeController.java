package top.lance.requestservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.lance.requestservice.openfeign.NodeService;
import top.lance.requestservice.vo.User;
import top.lance.requestservice.vo.UserVO;

import java.util.List;

/**
 * @author: lance
 * @createTime: 2025/02/28 15:17
 * @description:
 **/
@RestController
public class NodeController {
    @Resource
    private NodeService nodeService;

    // 创建用户
    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {
        // 调用 NodeService 的 createUser 方法
        return nodeService.createUser(user);
    }

    // 更新用户
    @PostMapping("/update-user")
    public UserVO updateUser(@RequestBody UserVO user) {
        // 调用 NodeService 的 updateUser 方法
        return nodeService.updateUser(user);
    }

    // 删除用户
    @DeleteMapping("/delete-user/{id}")
    public UserVO deleteUser(@PathVariable Integer id) {
        // 调用 NodeService 的 deleteUser 方法
       return nodeService.deleteUser(id);
    }

    // 获取用户列表
    @GetMapping("/users")
    public List<User> getUsers() {
        // 调用 NodeService 的 getUsers 方法
        return nodeService.getUsers();
    }
}
