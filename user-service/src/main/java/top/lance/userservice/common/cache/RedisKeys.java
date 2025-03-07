package top.lance.userservice.common.cache;

/**
 * @author: orange
 * @projectName: share-app-api
 * @description:
 */
public class RedisKeys {
    /**
     * 验证码Key
     */
    public static String getSmsKey(String phone) {
        return "sms:captcha:" + phone;
    }

    /**
     * accessToken Key
     */
    public static String getAccessTokenKey(String accessToken) {
        return "sys:access:" + accessToken;
    }

    /**
     * 获取用户 ID 密钥
     *
     * @param id id
     * @return {@link String}
     */
    public static String getUserIdKey(Integer id) {
        return "sys:userId:" + id;
    }
}
