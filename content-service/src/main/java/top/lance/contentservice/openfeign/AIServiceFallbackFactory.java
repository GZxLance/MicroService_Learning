package top.lance.contentservice.openfeign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import top.lance.contentservice.model.result.Result;

import java.util.Map;

/**
 * @author： Lance
 * @create： 2025/3/16
 * @Description AIServiceFallbackFactory
 **/
@Slf4j
@Component
public class AIServiceFallbackFactory implements FallbackFactory<Aiservice> {

    @Override
    public Aiservice create(Throwable cause) {
        log.error("OCR 服务调用异常", cause);
        return new Aiservice() {
            @Override
            public Result<Map<String, Object>> ocr(MultipartFile file) {
                return Result.ok(Map.of("error", "OCR 服务不可用"));
            }
        };
    }
}