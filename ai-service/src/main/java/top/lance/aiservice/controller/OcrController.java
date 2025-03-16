package top.lance.aiservice.controller;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.lance.aiservice.result.Result;
import top.lance.aiservice.service.OcrService;

import java.util.Map;

/**
 * @author： Lance
 * @create： 2025/3/16
 * @Description OcrController
 **/
@RestController
@AllArgsConstructor
public class OcrController {
    @Resource
    private OcrService ocrService;

    @PostMapping("/ocr")
    public Result<Map<String, Object>> ocr(@RequestBody MultipartFile file){
        try {
            // 调用 OCR 服务
            return Result.ok(ocrService.recongizeTextFromMultipartFile(file));
        } catch (Exception e) {
            // 返回错误信息
            return Result.ok(Map.of("error", e.getMessage()));
        }
    }

}