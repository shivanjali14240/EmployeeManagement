package com.bnt.emplyeemodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmployeeModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeModuleApplication.class, args);
	}

}
