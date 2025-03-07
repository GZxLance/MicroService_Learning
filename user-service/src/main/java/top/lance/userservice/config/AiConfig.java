package top.lance.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author: orange
 * @projectName: user-service
 * @description:
 */
@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class AiConfig {
    private String apiKey;
}