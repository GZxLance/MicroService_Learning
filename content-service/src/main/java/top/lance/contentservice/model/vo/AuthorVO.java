package top.lance.contentservice.model.vo;

import lombok.Data;

/**
 * @author: lance
 * @projectName: content-service
 * @description:
 */
@Data
public class AuthorVO {
    private Integer id;
    private String userName;
    private String avatarUrl;
}