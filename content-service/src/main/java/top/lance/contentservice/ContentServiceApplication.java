package top.lance.contentservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import top.lance.contentservice.config.RandomLoadBalancerConfig;

@SpringBootApplication
@MapperScan(basePackages = "top.lance.contentservice.mapper")
@EnableFeignClients(basePackages = "top.lance.contentservice.openfeign")
@LoadBalancerClient(name = "user-service",configuration = RandomLoadBalancerConfig.class)

public class ContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }

}
