package top.lance.contentservice.openfeign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lance.contentservice.model.result.Result;
import top.lance.contentservice.model.vo.AuthorVO;

/**
 * @author： Lance
 * @create： 2025/3/14
 * @Description UserServiceFallBackFactory
 **/
@Component
@Slf4j
public class UserServiceFallBackFactory implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable cause) {
        log.error("用户服务调用异常", cause);
        return new UserFeignClient() {
            @Override
            public Result<AuthorVO> getAuthorInfo(Integer id) {
                // 降级用户
                AuthorVO user = new AuthorVO();
                user.setId(-1);
                user.setUserName("异常的用户名");
                user.setAvatarUrl("https://chenpicture.oss-cn-shanghai.aliyuncs.com/favicon.png");
                return Result.ok(user);
            }
        };
    }
}