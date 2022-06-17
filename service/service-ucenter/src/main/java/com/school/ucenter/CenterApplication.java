package com.school.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 功能描述：
 *
 * @Package: com.school.ucenter
 * @author: Marvin-zl
 * @date: 2022/6/2 12:28
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.school"})
@MapperScan("com.school.ucenter.mapper")
public class CenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CenterApplication.class,args);
    }
}
