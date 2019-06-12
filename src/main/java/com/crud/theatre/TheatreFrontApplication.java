package com.crud.theatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

@SpringBootApplication
public class TheatreFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheatreFrontApplication.class, args);
    }
}
