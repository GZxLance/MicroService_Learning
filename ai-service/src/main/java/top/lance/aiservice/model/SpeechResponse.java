package top.lance.aiservice.model;

import lombok.Data;

@Data
public class SpeechResponse {
    private String reason;
    private Integer error_code;
    private String[] result;
    private String errorMessage;

    @Data
    public static class Result {
        private String text;
    }
}