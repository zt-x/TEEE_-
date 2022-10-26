package com.teee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Xuuu
 */
@EnableTransactionManagement
@SpringBootApplication
public class TEEEApplication {
    public static void main(String[] args) {
        SpringApplication.run(TEEEApplication.class, args);
    }
}
