package top.lance.aiservice.service;

import org.springframework.stereotype.Service;
import top.lance.aiservice.model.SpeechResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpeechService {

    public SpeechResponse detectSpeechFromUrl(String ossUrl, String format) {
        try {
            // 从OSS URL获取音频数据
            URL url = new URL(ossUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            byte[] audioData = connection.getInputStream().readAllBytes();
            String base64Speech = Base64.getEncoder().encodeToString(audioData);
            
            // 调用语音识别API
            Map<String, String> params = new HashMap<>();
            params.put("key", "4edecf2f9a4c8237914057eac75d2dca");
            params.put("speech", base64Speech);
            params.put("format", format);

            URL apiUrl = new URL("http://apis.juhe.cn/speechDetect/index");
            HttpURLConnection apiConnection = (HttpURLConnection) apiUrl.openConnection();
            apiConnection.setRequestMethod("POST");
            apiConnection.setDoOutput(true);

            String urlParameters = params.entrySet().stream()
                .map(entry -> {
                    try {
                        return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                    } catch (Exception e) {
                        throw new RuntimeException("参数编码失败", e);
                    }
                })
                .collect(Collectors.joining("&"));

            try (OutputStream os = apiConnection.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(apiConnection.getInputStream(), StandardCharsets.UTF_8))) {
                String response = br.lines().collect(Collectors.joining());
                return new com.google.gson.Gson().fromJson(response, SpeechResponse.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("语音识别失败", e);
        }
    }
}