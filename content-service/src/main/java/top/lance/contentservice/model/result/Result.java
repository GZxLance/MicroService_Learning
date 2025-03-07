package top.lance.contentservice.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author： Lance
 * @create： 2025/3/7
 * @Description Result
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    // 状态码
    private String message;
    // 消息
    private T data;
    // 数据

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }
}