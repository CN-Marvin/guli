package com.school.cms;

/**
 * 功能描述：
 *
 * @Package: com.school.guli
 * @author: Marvin-zl
 * @date: 2022/5/31 19:08
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.school"})
@MapperScan("com.school.cms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
