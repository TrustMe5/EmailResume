package org.emailresume;

import org.emailresume.controller.EmailController;
import org.emailresume.controller.VerifyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

@Configuration
@EnableAutoConfiguration(
        exclude={GsonAutoConfiguration.class,
                JacksonAutoConfiguration.class,
                RedisAutoConfiguration.class,
                WebSocketAutoConfiguration.class
})
public class Main extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes (RequestAttributes requestAttributes,
                                                           boolean includeStackTrace){
                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
                errorAttributes.remove("error");
                errorAttributes.remove("exception");
                errorAttributes.remove("timestamp");
                errorAttributes.remove("path");
                errorAttributes.put("status", "0");
                return errorAttributes;
            }
        };
    }

    @Bean
    public EmailController getMessageController(){
        return new EmailController();
    }

    @Bean
    public VerifyController getVerifyController(){
        return new VerifyController();
    }
}
