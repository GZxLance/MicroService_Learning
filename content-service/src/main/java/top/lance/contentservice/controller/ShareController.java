package top.lance.contentservice.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lance.contentservice.model.vo.ShareVO;
import top.lance.contentservice.service.ShareService;

/**
 * @author: lance
 * @projectName: content-service
 * @description:
 */
@RestController
@RequestMapping("/share")
@AllArgsConstructor
public class ShareController {



    private final ShareService shareService;

    @GetMapping("/{id}")
    @SentinelResource(value = "/share/{id}")
    public ShareVO getShareDetail(@PathVariable Integer id) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return shareService.getShareWithAuthor(id);
    }
}