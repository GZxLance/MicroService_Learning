package top.lance.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lance.userservice.common.result.Result;
import top.lance.userservice.config.AiConfig;
import top.lance.userservice.service.CommonService;

import java.io.IOException;

@Tag(name = "基础服务")
@RestController
@RequestMapping("/common")
@AllArgsConstructor
public class CommonController {
    private final CommonService commonService;

    @PostMapping("/sendSms")
    @Operation(summary = "发送短信")
    public Result<Object> sendSms(@RequestParam("phone") String phone) {
        commonService.sendSms(phone);
        return Result.ok();
    }

    @PostMapping(value = "/upload/img")
    @Operation(summary = "图片上传")
    public Result<Object> upload(@RequestBody MultipartFile file) {
        return Result.ok(commonService.upload(file));
    }

    @PostMapping(value = "/ai")
    @Operation(summary = "ai服务")
    public Result<String> getAnswer(@RequestParam String content) throws IOException {
        return Result.ok(commonService.ai(content));
    }

    @Resource
    private AiConfig aiConfig;

    @GetMapping("/test-config")
    public String testConfig() {
        return "DeepSeek API Key: " + aiConfig.getApiKey();
    }

}
