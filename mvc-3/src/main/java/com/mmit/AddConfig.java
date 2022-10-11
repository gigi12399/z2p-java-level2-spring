package com.mmit;

import java.nio.file.Path;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AddConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "upload-photos";
		Path dirPath = Path.of(dirName);
		
		String uploadPath = dirPath.toFile().getAbsolutePath(); //C://spring-workspace/mbc-3/upload-photos/
		
		// /upload-photos/**
		registry.addResourceHandler("/" + dirName + "/**")
		.addResourceLocations("file:///" + uploadPath + "/"); 
	}
}
