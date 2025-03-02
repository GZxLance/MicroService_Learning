package top.lance.contentservice.model.vo;

import lombok.Data;
import top.lance.contentservice.model.entity.Share;

/**
 * @author: lance
 * @projectName: content-service
 * @description:
 */
@Data
public class ShareVO {
    private Share share;
    private AuthorVO author;
}