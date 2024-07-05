package com.softserve.itacademy;

import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ToDoListApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        ConfigurableApplicationContext ap = SpringApplication.run(ToDoListApplication.class, args);
        RoleService userService = ap.getBean(RoleService.class);
        userService.create(null);

    }

}
