package top.lance.userservice.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: lance
 * @projectName: user-service
 * @description:
 */
@Data
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String mobile;
    private String password;
    private String userName;
    private String avatarUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}