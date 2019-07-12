# Bean懒加载

Bean的懒加载是针对单实例Bean来说的，在单实例Bean中，IOC容器启动会调用方法创建对象放到IOC容器中，但使用懒加载那么在IOC容器启动不会将对象创建放到IOC容器中，而是在第一次使用Bean创建对象，并初始化

```java
package com.hzj.config;


import com.hzj.bean.Person;
import org.springframework.context.annotation.*;

/**
 * @author hzj
 */
@Configuration
@ComponentScan(value = "com.hzj")
public class MainConfig {


    /**
     * prototype: 多实例: ioc容器启动并调用方法创建对象放在容器中,每次获取的时候才会调用方法创建对象
     * singleton: 单实例(默认值),IOC容器启动会调用方法创建对象放到IOC容器中，以后每次获取就是从容器(map.get())中拿
     * request: 同一次请求创建一次实例(web环境,用的不多)
     * session: 同一次session创建一次实例(web环境,用的不多)
     * 
     * 懒加载: 单实例Bean,默认在容器启动的时候创建对象,容器启动不创建对象，第一次使用Bean创建对象，并初始化，以后每次使用都不会在创建该对象，而是直接从IOC容器中拿
     * 
     * @return
     */
    @Scope("singleton")
    //@Scope("singleton") = @Scope
    @Lazy
    @Bean("person")
    public Person person() {
        return new Person("hzj", 18);
    }
}
```