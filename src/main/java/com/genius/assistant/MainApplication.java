package com.genius.assistant;


import com.genius.assistant.pool.RegexPool;
import com.genius.assistant.util.token.JWTTokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JWTTokenProperties.class, RegexPool.class})
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

    }
}
