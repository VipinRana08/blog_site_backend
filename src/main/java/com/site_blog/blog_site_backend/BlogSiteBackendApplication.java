package com.site_blog.blog_site_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BlogSiteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogSiteBackendApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager transaction(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

}
