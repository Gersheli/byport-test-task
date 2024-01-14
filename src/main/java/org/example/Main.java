package org.example;

import org.example.config.SpringConfig;
import org.example.service.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        Service app = applicationContext.getBean(Service.class);
        app.start();
    }
}