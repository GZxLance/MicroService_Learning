package top.lance.contentservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.lance.contentservice.model.vo.AuthorVO;

/**
 * @author: lance
 * @projectName: request-service
 * @description:
 */
@FeignClient("user-service")
public interface UserFeignClient {
    @GetMapping("/user/{id}")
    AuthorVO getAuthorInfo(@PathVariable Integer id);
}