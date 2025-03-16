package top.lance.aiservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author： Lance
 * @create： 2025/3/16
 * @Description OcrService
 **/
public interface OcrService {
    Map<String,Object> recongizeTextFromMultipartFile(MultipartFile file) throws Exception;
}
