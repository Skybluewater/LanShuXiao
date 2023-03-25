package com.bitmotel.lanshuxiao;

import com.bitmotel.lanshuxiao.component.PasswordEncoderI;
import com.bitmotel.lanshuxiao.component.PasswordEncoderImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LanShuXiaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(LanShuXiaoApplication.class, args);
    }

    @Bean
    public PasswordEncoderI passwordEncoderI() {
        return new PasswordEncoderImpl();
    }
}
