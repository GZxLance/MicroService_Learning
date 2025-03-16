package top.lance.aiservice.service.impl;

import com.aliyun.ocr_api20210707.models.RecognizeAllTextResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.Common;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lance.aiservice.service.OcrService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;

/**
 * @author： Lance
 * @create： 2025/3/16
 * @Description OcrServiceImpl
 **/
@Service
public class OcrServiceImpl implements OcrService {
    // 创建阿里云 OCR 客户端
    public static com.aliyun.ocr_api20210707.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId("LTAI5tN9BarZcbY3ejmLFjdu")
                // 从环境变量获取 AccessKeyId
                .setAccessKeySecret("zbJBmqWwRT61UwjwaN5AraY6Kzeuid");
        // 从环境变量获取 AccessKeySecret
        config.endpoint = "ocr-api.cn-hangzhou.aliyuncs.com";
        // 设置接入地址
        return new com.aliyun.ocr_api20210707.Client(config);
    }

    @Override
    public Map<String, Object> recongizeTextFromMultipartFile(MultipartFile file) throws Exception {
        // 创建 OCR 客户端
        com.aliyun.ocr_api20210707.Client client = createClient();

        // 将 MultipartFile 转换为 Base64 编码
        byte[] fileBytes = file.getBytes();
        String imageBase64 = Base64.getEncoder().encodeToString(fileBytes);

        // 将 Base64 字符串转换为 InputStream
        byte[] decodedBytes = Base64.getDecoder().decode(imageBase64);
        InputStream inputStream = new ByteArrayInputStream(decodedBytes);

        // 创建识别请求
        com.aliyun.ocr_api20210707.models.RecognizeAllTextRequest recognizeAllTextRequest =
                new com.aliyun.ocr_api20210707.models.RecognizeAllTextRequest()
                        .setBody(inputStream)
                        // 设置图片的 InputStream
                        .setType("General");
        // 设置识别类型，例如 "General" 或 "IdCard"

        // 设置运行时选项
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();

        try {
            // 调用 OCR API 进行识别
            RecognizeAllTextResponse resp = client.recognizeAllTextWithOptions(recognizeAllTextRequest, runtime);
            // 将响应体转换为 Map（JSON 格式）
            return Common.toMap(resp.body);
        } catch (TeaException error) {
            // 处理阿里云 SDK 异常
            System.out.println("Error Message: " + error.getMessage());
            System.out.println("Diagnostic: " + error.getData().get("Recommend"));
            throw new RuntimeException("OCR recognition failed: " + error.getMessage());
        } catch (Exception e) {
            // 处理其他异常
            throw new RuntimeException("OCR recognition failed: " + e.getMessage());
        }
    }
}