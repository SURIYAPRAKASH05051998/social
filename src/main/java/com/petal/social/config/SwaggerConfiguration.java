package com.petal.social.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	 @Bean
	    public Docket productApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()                 
	                .apis(RequestHandlerSelectors.basePackage("com.petal.social"))
	                .paths(PathSelectors.any())
	                .build()
	                .apiInfo(metaData())
	                .globalResponseMessage(RequestMethod.GET,getErrorResponseCodeDetails())
	                				.apiInfo(metaData());
		}
		
	private List<ResponseMessage> getErrorResponseCodeDetails() {
		return newArrayList(
				new ResponseMessageBuilder()   
			    .code(500)
			    .message("Please Try Again Later")
			    .build(),
			    new ResponseMessageBuilder() 
			      .code(403)
			      .message("Forbidden!")
			      .build(),
			    new ResponseMessageBuilder() 
			      .code(401)
			      .message("Not Authorized!")
			      .build(),
		        new ResponseMessageBuilder() 
			      .code(404)
			      .message("Not Found!")
			      .build());
	
	        
	     
	 }
	 
	 
	 private ApiInfo metaData() {
		 return new ApiInfoBuilder().title("Spring Boot REST API")
		            .description("Petal Social Rest Api")
		            .license("Petal Social Service")
		            .licenseUrl("petalsautomation.com")
		            .version("1.0.0")
		            .build();
	    }
	}

