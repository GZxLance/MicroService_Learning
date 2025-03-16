package top.lance.contentservice.openfeign;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import top.lance.contentservice.model.result.Result;

import java.util.Map;

/**
 * @author： Lance
 * @create： 2025/3/16
 * @Description Aiservice
 **/
@FeignClient(value = "ai-service",configuration = MultipartSupportConfig.class,fallbackFactory = AIServiceFallbackFactory.class)
public interface Aiservice {
    @PostMapping(value = "ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<Map<String,Object>> ocr(@RequestPart("file") MultipartFile file);
}
class MultipartSupportConfig {
    @Bean
    public Encoder feignFormEncoder () {
        return new SpringFormEncoder();
    }
}

