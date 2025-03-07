package top.lance.userservice.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lance.userservice.common.cache.RedisCache;
import top.lance.userservice.common.cache.RedisKeys;
import top.lance.userservice.common.exception.ServerException;
import top.lance.userservice.config.AiConfig;
import top.lance.userservice.config.OssConfig;
import top.lance.userservice.service.CommonService;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author: orange
 * @projectName: share-app-api
 * @description:
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {
    // 允许上传文件(图片)的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
    ;
    @Resource
    private RedisCache redisCache;
    @Resource
    private OssConfig ossConfig;
    @Resource
    private AiConfig aiConfig;

    @Override
    public void sendSms(String phone) {
        redisCache.set(RedisKeys.getSmsKey(phone), 6666, 60);
    }

    @Override
    public String upload(MultipartFile file) {
        String returnImgUrl;

        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            // 如果图片格式不合法
            throw new ServerException("图片格式不正确");
        }
//        获取文件原名称
        String originalFilename = file.getOriginalFilename();
// 获取文件类型
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新文件名称
        String newFileName = UUID.randomUUID().toString() + fileType;
        // 构建日期路径, 例如：OSS目标文件夹/2024/04/31/文件名
        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
// 文件上传的路径地址
        String uploadUrl = filePath + "/" + newFileName;
// 获取文件输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        /**
         * 现在阿里云0SS 默认图片上传ContentType是image/jpeg
         * 也就是说，获取图片链接后，图片是下载链接，而并非在线浏览链接，
         * 因此，这里在上传的时候要解决ContentType的问题，将其改为image/jpg
         */
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpg");

        //读取配置文件中的属性
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        String bucketName = ossConfig.getBucketName();
        // 创建OSSClient
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 文件上传至阿里云OSS
        ossClient.putObject(bucketName, uploadUrl, inputStream, meta);
        // 获取文件上传后的图片返回地址
        returnImgUrl = "https://" + bucketName + "." + endpoint + "/" + uploadUrl;
        return returnImgUrl;
    }

    @Override
    public String ai(String userQuestion) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        // 构建请求体，动态插入用户问题
        String requestBody = String.format("{\n  \"messages\": [\n    {\n      \"content\": \"You are a helpful assistant\",\n      \"role\": \"system\"\n    },\n    {\n      \"content\": \"%s\",\n      \"role\": \"user\"\n    }\n  ],\n  \"model\": \"deepseek-chat\",\n  \"frequency_penalty\": 0,\n  \"max_tokens\": 2048,\n  \"presence_penalty\": 0,\n  \"response_format\": {\n    \"type\": \"text\"\n  },\n  \"stop\": null,\n  \"stream\": false,\n  \"stream_options\": null,\n  \"temperature\": 1,\n  \"top_p\": 1,\n  \"tools\": null,\n  \"tool_choice\": \"none\",\n  \"logprobs\": false,\n  \"top_logprobs\": null\n}", userQuestion);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);

        // 构建请求
        Request request = new Request.Builder()
                .url("https://api.deepseek.com/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + aiConfig.getApiKey())
                .build();

        // 发送请求并获取响应
        Response response = client.newCall(request).execute();

        // 检查响应是否成功
        if (response.isSuccessful()) {
            // 解析响应体并提取 content 数据
            String responseBody = response.body().string();

            // 使用 FastJSON 解析返回的 JSON 数据
            JSONObject jsonObject = JSON.parseObject(responseBody);
            String content = jsonObject.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            return content.trim();  // 返回 content 数据，去除前后空格
        } else {
            // 处理错误情况
            throw new IOException("Unexpected code " + response);
        }
    }
}

