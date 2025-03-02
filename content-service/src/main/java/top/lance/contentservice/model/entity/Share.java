package top.lance.contentservice.model.entity;

/**
 * @author: lance
 * @projectName: content-service
 * @description:
 */
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_share")
public class Share {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String title;
    private Boolean isOriginal;
    private String author;
    private String cover;
    private String summary;
    private Integer price;
    private String downloadUrl;
    private Integer buyCount;
    private Boolean showFlag;
    private String auditStatus;
    private String reason;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
