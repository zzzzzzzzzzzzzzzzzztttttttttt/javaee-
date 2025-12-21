package org.example.javaee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.javaee.mapper")
public class JavaEeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaEeApplication.class, args);
    }

}
