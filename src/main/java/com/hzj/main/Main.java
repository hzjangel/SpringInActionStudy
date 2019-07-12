package com.hzj.main;

import com.hzj.bean.Person;
import com.hzj.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = (Person) application.getBean("person");
        System.out.println(person);

    }
}
