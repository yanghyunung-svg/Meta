package com.meta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.meta") // 컨트롤러 스캔 보장
@MapperScan("com.meta.mapper")
public class MetaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetaApplication.class, args);
    }
}
