package top.lance.contentservice.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.lance.contentservice.model.result.Result;
import top.lance.contentservice.openfeign.Aiservice;

import java.util.Map;

/**
 * @author： Lance
 * @create： 2025/3/16
 * @Description AiController
 **/
@RestController
public class AiController {
    @Resource
    private Aiservice aiService;
    @PostMapping("/ocr")
    public Result<Map<String,Object>> ocr(@RequestPart MultipartFile file){
        return Result.ok(aiService.ocr(file).getData());
    }
}
