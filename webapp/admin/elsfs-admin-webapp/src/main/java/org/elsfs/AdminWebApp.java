package org.elsfs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@MapperScan("org.elsfs.admin.infrastructure.repository.mybatis")
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@ComponentScan(basePackages = { "org.elsfs.admin" })
public class AdminWebApp {

    public static void main(String[] args) {
        SpringApplication.run(AdminWebApp.class, args);
    }

}
