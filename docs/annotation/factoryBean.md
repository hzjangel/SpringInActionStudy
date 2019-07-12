# FactoryBean

除了使用`@Import`创建Bean之外，还可以使用`FactoryBean`来创建Bean

## 文件目录

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── hzj
│   │           ├── bean
│   │           │   ├── Color.java
│   │           └── config
│   │               └── MainConfig.java
│   └── resources
└── test
    └── java
        └── com
            └── hzj
                └── test
                    └── IOCTest.java


```

## 使用

1. 编写一个类实现`FactoryBean`，并重写`getObject`、`getObjectType`、`isSingleton`这三个方法

```java
package com.hzj.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 *
 * @author hzj
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    /**
     * 设置Bean
     * @return 创建的Bean
     * @throws Exception
     */
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    /**
     * 设置Bean的类型
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    /**
     * 设置单例
     * @return true: 单例; false: 不单例
     */
    @Override
    public boolean isSingleton() {
        return false;
    }
}

```

2. 创建一个Bean

```java
package com.hzj.config;


import com.hzj.bean.Blue;
import com.hzj.bean.Color;
import com.hzj.bean.ColorFactoryBean;
import com.hzj.bean.Person;
import com.hzj.condition.LinuxCondition;
import com.hzj.condition.WindowsCondition;
import org.springframework.context.annotation.*;

/**规则
 * @author hzj
 */
@Configuration
@ComponentScan(value = "com.hzj")
@Import({Blue.class, Color.class, MyImportSelect.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig {

	//...

    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
}


```

这里需要注意的是，这里通过ColorFactoryBean创建的Bean最终得到了类型是`Color`类型，而不是`ColorFactoryBean`类型，如果要获取`ColorFactoryBean`类型的实例，需要添加`&`

```java
public class IOCTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);

    @Test
    public void FactoryBeanTest() {
        Object colorFactoryBean = applicationContext.getBean("colorFactoryBean");
        System.out.println(colorFactoryBean.getClass());
        Object colorFactory = applicationContext.getBean("&colorFactoryBean");
        System.out.println(colorFactory.getClass());
    }
}
```