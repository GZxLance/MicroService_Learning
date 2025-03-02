package top.lance.requestservice.vo;

import lombok.Data;

/**
 * @author: lance
 * @createTime: 2025/02/28 16:56
 * @description:
 **/
@Data
public class UserVO {
    private Integer id;
    private String mobile;
    private String password;
    private String user_name;
    private String avatar_url;
}
