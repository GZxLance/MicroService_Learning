package top.lance.contentservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author： Lance
 * @create： 2025/3/7
 * @Description CryProperties
 **/
@Component
@ConfigurationProperties(prefix = "lance")
@Data
public class CryProperties {
    private String username;
    private String job;
    private Boolean serviceFlag;

}