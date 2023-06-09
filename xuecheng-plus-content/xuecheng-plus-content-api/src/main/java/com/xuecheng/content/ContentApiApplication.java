package com.xuecheng.content;/**
 * @author 朱焕杰
 * @version 1.0
 * @date 2023/2/22 14:40
 */

import com.spring4all.swagger.EnableSwagger2Doc;
import com.xuecheng.content.config.MinioConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 课程模块启动类
 * @date 2023/2/22 14:40
 */
@SpringBootApplication
@EnableSwagger2Doc
@ComponentScan("com.xuecheng")
@EnableConfigurationProperties(MinioConfig.class)
public class ContentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApiApplication.class, args);
    }
}
