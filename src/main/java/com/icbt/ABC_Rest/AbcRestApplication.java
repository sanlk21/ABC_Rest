package com.icbt.ABC_Rest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AbcRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbcRestApplication.class, args);
	}
    @Bean
	public ModelMapper modelMapper(){
		return  new ModelMapper();
	}
}
