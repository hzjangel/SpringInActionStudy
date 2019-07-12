# 注解配置

## 文件目录

```
src/
├── main
│   ├── java
│   │   └── com
│   │       └── hzj
│   │           ├── bean
│   │           │   └── Person.java
│   │           ├── config
│   │           │   └── MainConfig.java
│   │           ├── controller
│   │           │   └── BookController.java
│   │           ├── dao
│   │           │   └── BookDao.java
│   │           ├── main
│   │           │   └── Main.java
│   │           └── service
│   │               └── BookService.java
│   └── resources
└── test
    └── java
        └── com
            └── hzj
                └── test
                    └── IOCTest.java

```
## 获取单个Bean


1. 编写一个实体类Person

```java
package com.hzj.bean;

/**
 * @author hzj
 */
public class Person {

    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

```

2. 使用注解编写一个配置文件

```java
package com.hzj.config;

import com.hzj.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hzj
 */
@Configuration
public class MainConfig {

	//Bean不指定value，value的默认是方法名
    @Bean("person")
    public Person person() {
        return new Person("hzj", 18);
    }
}
```

3. 编写测试用例

```java
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

```

## 自动装配

1. 在`controller`层创建一个类

```java
package com.hzj.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BookController {
}

```

2. 在`service`层创建一个类

```java
package com.hzj.service;

import org.springframework.stereotype.Service;

@Service
public class BookService {
}

```

3. 在`dao`层创建一个类

```java
package com.hzj.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
}

```

4. 更改配置文件

```java
package com.hzj.config;


import com.hzj.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hzj
 */
@Configuration
@ComponentScan(value = "com.hzj")

//@ComponentScan
//  value: 指定要扫描的包
public class MainConfig {

    @Bean("person")
    public Person person() {
        return new Person("hzj", 18);
    }
}

```

5. 编写测试文件

```java
package com.hzj.test;

import com.hzj.config.MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest {


    @SuppressWarnings("resource")
    @Test
    public void test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}

```