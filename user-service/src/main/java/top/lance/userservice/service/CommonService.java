package top.lance.userservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: orange
 * @projectName: share-app-api
 * @description:
 */
public interface CommonService {
    /**
     * 发送短信
     *
     * @param phone 手机号
     */
    void sendSms(String phone);

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 上传后的url
     */
    String upload(MultipartFile file);

//    ai
    String ai(String userQuestion)throws IOException;
}
