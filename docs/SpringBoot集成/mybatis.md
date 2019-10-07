# SpringBoot集成MyBatis

## 文件目录

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── hzj
│   │           └── springbootmybatis
│   │               ├── config
│   │               │   └── MybatisConfig.java
│   │               ├── mappper
│   │               └── SpringBootMybatisApplication.java
│   └── resources
│       ├── application.yml
│       ├── mybatis
│       │   ├── mapper
│       │   │   └── UserMapper.xml
│       │   └── mybatis-config.xml
│       ├── static
│       └── templates
```

## 使用注解进行开发

使用注解进行开发，不需要做任何配置，只需要导入需要的`jar`包即可，当然数据库连接啥的配置还是需要的

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
<groupId>org.mybatis.spring.boot</groupId>
<artifactId>mybatis-spring-boot-starter</artifactId>
<version>2.1.0</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

如果需要开启驼峰式命名法，只需要将`ConfigurationCustomizer`注入到IOC容器中

```java
package com.hzj.springbootmybatis.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hzj
 */
@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {

            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                //开启驼峰式命名法
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}

```

::: tip
如果不想在每个Mapper都写上`@Mapper`注解，那么就可以使用`@MapperScan(basePackages = {"com.hzj.springbootmybatis.mappper"})`进行扫描就可以了
:::

## 使用xml进行配置

使用xml进行配置比使用注解配置麻烦一点


### 步骤

1. 编写mybatis的xml配置文件

2. 在`application.yml`指名mybatis的配置文件的路径

### 代码实现

1. mybatis配置文件的编写

如果里面不需要什么配置的话可以什么都不写

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

</configuration>
```

2. 在`application.yml`文件中指名mybatis的配置文件路径,

```yaml
# 省略数据库连接啥的，具体可以看SpringBoot集成Druid
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml
  mapper-locations: classpath:mybatis/mapper/*.xml
```

