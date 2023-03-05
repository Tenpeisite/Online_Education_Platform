package com.xuecheng;


import com.spring4all.swagger.EnableSwagger2Doc;
import com.xuecheng.media.config.MinioConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableSwagger2Doc
@SpringBootApplication
@EnableConfigurationProperties(MinioConfig.class)
public class MediaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MediaApplication.class, args);
	}
}