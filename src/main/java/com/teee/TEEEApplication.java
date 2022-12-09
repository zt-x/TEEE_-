package com.teee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Xuuu
 */
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class TEEEApplication {
    public static void main(String[] args) {
        SpringApplication.run(TEEEApplication.class, args);
    }
}
