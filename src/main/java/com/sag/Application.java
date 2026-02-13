package com.sag;


import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = {"com.sag"})
@ImportResource({"classpath*:common.resources/common-beans.xml"})
public class Application {

 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
