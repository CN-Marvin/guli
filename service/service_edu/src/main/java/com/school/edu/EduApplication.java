package com.school.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 功能描述：
 *
 * @Package: com.school.edu
 * @author: Marvin-zl
 * @date: 2022/5/23 11:55
 */
@SpringBootApplication
@ComponentScan("com.school")
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
