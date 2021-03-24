package com.smt.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

import com.douglei.orm.spring.boot.starter.TransactionComponentScan;
import com.smartone.ddm.util.MappingContextRegisterListener;

@EnableEurekaClient
@SpringBootApplication
@TransactionComponentScan(packages = { "com.ibs","com.smartone","com.smt.dev" })
@ComponentScan(basePackages= {"com.ibs","com.smartone", "com.smt.dev"})
public class SmtDevApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SmtDevApplication.class);
		springApplication.addListeners(new MappingContextRegisterListener());
		springApplication.run(args);
	}
}
