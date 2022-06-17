package com.school.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 功能描述：
 *
 * @Package: com.school.statistics
 * @author: Marvin-zl
 * @date: 2022/6/8 14:28
 */
@SpringBootApplication
@ComponentScan("com.school")
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.school.statistics.mapper")
@EnableScheduling
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
