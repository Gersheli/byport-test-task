package org.example;

import org.example.config.SpringConfig;
import org.example.app.App;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        App app = applicationContext.getBean(App.class);
        app.start();
    }
}