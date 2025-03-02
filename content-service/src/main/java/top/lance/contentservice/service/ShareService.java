package top.lance.contentservice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.lance.contentservice.mapper.ShareMapper;
import top.lance.contentservice.model.entity.Share;
import top.lance.contentservice.model.vo.AuthorVO;
import top.lance.contentservice.model.vo.ShareVO;
import top.lance.contentservice.openfeign.UserFeignClient;

/**
 * @author: lance
 * @projectName: content-service
 * @description:
 */
@Service
public class ShareService {
    @Resource
    private ShareMapper shareMapper;

    @Resource
    private UserFeignClient userFeignClient;

    public ShareVO getShareWithAuthor(Integer id) {
        Share share = shareMapper.selectById(id);
        AuthorVO author = userFeignClient.getAuthorInfo(share.getUserId());
        ShareVO shareVO = new ShareVO();
        shareVO.setShare(share);
        shareVO.setAuthor(author);
        return shareVO;
    }
}