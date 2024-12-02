package org.simple.dbms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.simple.dbms")
public class DbmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbmsApplication.class, args);
    }

}
