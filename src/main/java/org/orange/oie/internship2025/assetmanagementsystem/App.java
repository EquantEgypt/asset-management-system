package org.orange.oie.internship2025.assetmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
