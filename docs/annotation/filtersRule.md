# 过滤规则

## 文件目录

与注解配置的目录一样，新增了`MyTypeFilters.java`文件

```
src/
├── main
│   ├── java
│   │   └── com
│   │       └── hzj
│   │           ├── bean
│   │           │   └── Person.java
│   │           ├── config
│   │           │   ├── MainConfig.java
│   │           │   └── MyTypeFilters.java
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


## 指定排除那些组件

1. 使用`excludeFilters`进行排除

```java
package com.hzj.config;


import com.hzj.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author hzj
 */
@Configuration
@ComponentScan(value = "com.hzj", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
                Controller.class, Service.class
        })
}, useDefaultFilters = false)

//@ComponentScan
//  value: 指定要扫描的包
//  excludeFilters = Filter[] :指定扫描的时候按照什么规则排除那些组件
public class MainConfig {

    @Bean("person")
    public Person person() {
        return new Person("hzj", 18);
    }
}

```

2. 指定包含那些组件

1. 使用`includeFilters`进行包含

```java
package com.hzj.config;


import com.hzj.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author hzj
 */
@Configuration
@ComponentScan(value = "com.hzj", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
                Controller.class, Service.class
        })
}, useDefaultFilters = false)

//@ComponentScan
//  value: 指定要扫描的包
//  includeFilters = Filter[] :指定扫描的时候只需要包含那些组件,注意需要禁用默认扫描规则
public class MainConfig {

    @Bean("person")
    public Person person() {
        return new Person("hzj", 18);
    }
}

```

## 过滤规则

1. 按照注解进行过滤

```java
//FilterType.ANNOTATION: 按照注解进行过滤
@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
	Controller.class, Service.class
})
```

2. 按照类型进行过滤

```java
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
	BookService.class
})
```

3. 使用ASPECTJ表达式

```java
type = FilterType.ASPECTJ
```

4. 使用正则表达式

```java
type = FilterType.REGEX
```

5. 自定义规则

编写自定义规则类

```java
package com.hzj.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author hzj
 */
public class MyTypeFilters implements TypeFilter {

    /**
     *
     * @param metadataReader 读取到当前正在扫描的类的信息
     * @param metadataReaderFactory 可以获取到其他任何类的信息
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类资源(类的路径)
        Resource resource = metadataReader.getResource();
        String className = classMetadata.getClassName();
        System.out.println(className);
        //只要包含Book返回true(通过)
        if (className.contains("Book")) {
            return true;
        }

        return false;
    }
}

```

使用自定义规则

```java
@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {
	MyTypeFilters.class
})
```