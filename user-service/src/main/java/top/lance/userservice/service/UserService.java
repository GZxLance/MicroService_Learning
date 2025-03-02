package top.lance.userservice.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.lance.userservice.mapper.UserMapper;
import top.lance.userservice.model.entity.User;

/**
 * @author: lance
 * @projectName: user-service
 * @description:
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User getById(Integer id) {
        return userMapper.selectById(id);
    }
}
