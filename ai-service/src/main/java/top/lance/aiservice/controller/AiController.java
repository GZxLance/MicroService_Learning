package top.lance.aiservice.controller;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.lance.aiservice.test.aiTest;


/**
 * @author： Lance
 * @create： 2025/2/22
 * @Description AiController
 **/
@RestController
public class AiController {

    @GetMapping("/ask")
    public String makeQuestion(@RequestParam String content) throws NoApiKeyException, InputRequiredException {
        GenerationResult result = aiTest.callWithMessage(content);
        System.out.println("ai回复：" +
                result.getOutput().getChoices().get(0).getMessage().getReasoningContent() );
        return "思考过程：" +
                result.getOutput().getChoices().get(0).getMessage().getReasoningContent();
    }
}
