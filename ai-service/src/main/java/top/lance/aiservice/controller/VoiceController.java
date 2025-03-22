package top.lance.aiservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.lance.aiservice.model.SpeechResponse;
import top.lance.aiservice.service.SpeechService;

@Tag(name = "语音识别接口")
@RestController
@RequiredArgsConstructor
public class VoiceController {
    private final SpeechService speechService;

    @PostMapping("/voice/detect/url")
    public SpeechResponse detectSpeechFromUrl(@RequestParam String url, @RequestParam(defaultValue = "pcm") String format) {
        return speechService.detectSpeechFromUrl(url, format);
    }
}
