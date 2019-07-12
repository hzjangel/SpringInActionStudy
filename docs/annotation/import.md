# Import注解

## 文件目录

```
src/
├── main
│   ├── java
│   │   └── com
│   │       └── hzj
│   │           ├── bean
│   │           │   ├── Blue.java
│   │           │   ├── Color.java
│   │           │   ├── Person.java
│   │           │   ├── RainBow.java
│   │           │   ├── Red.java
│   │           │   └── Yellow.java
│   │           └── config
│   │               ├── MainConfig.java
│   │               ├── MyImportBeanDefinitionRegistrar.java
│   │               └── MyImportSelect.java

```

## 单独使用

直接导入需要的组件，id的默认值是全类名

```java

package com.hzj.config;


import com.hzj.bean.Blue;
import com.hzj.bean.Color;
import com.hzj.bean.Person;
import com.hzj.condition.LinuxCondition;
import com.hzj.condition.WindowsCondition;
import org.springframework.context.annotation.*;

/**规则
 * @author hzj
 */
@Configuration
@ComponentScan(value = "com.hzj")
@Import({Blue.class, Color.class})
public class MainConfig {

}

```

## 与ImportSelector配合使用

1. 只需要定义一个类实现`ImportSelector`接口，实现`selectImports`方法，返回一个字符串数组，数组的值是需要导入组件的全类名

```java
package com.hzj.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelect implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.hzj.bean.Yellow", "com.hzj.bean.Red"};
    }
}

```

2. 与@Import配合使用

```java
package com.hzj.config;


import com.hzj.bean.Blue;
import com.hzj.bean.Color;
import com.hzj.bean.Person;
import com.hzj.condition.LinuxCondition;
import com.hzj.condition.WindowsCondition;
import org.springframework.context.annotation.*;

/**规则
 * @author hzj
 */
@Configuration
@ComponentScan(value = "com.hzj")
@Import({Blue.class, Color.class, MyImportSelect.class})
public class MainConfig {

}
```

3. 与`ImportBeanDefinitionRegistrar`配合使用

1. 定义一个类实现`ImportBeanDefinitionRegistrar`类，在重写`registerBeanDefinitions`方法

```java
package com.hzj.config;

import com.hzj.bean.RainBow;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author hzj
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * 手动注册Bean
     * @param annotationMetadata 当前类的注解信息
     * @param beanDefinitionRegistry beanDefinition的注册类
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        //判断Bean定义的注册类是否包含red
        boolean isRed = beanDefinitionRegistry.containsBeanDefinition("com.hzj.bean.Red");
        //判断Bean定义的注册类是否包含blue
        boolean isBlue = beanDefinitionRegistry.containsBeanDefinition("com.hzj.bean.Blue");

        if (isRed && isBlue) {

            //指定Bean定义信息
            BeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
            //注册一个Bean，指定bean名
            beanDefinitionRegistry.registerBeanDefinition("bainBow",beanDefinition);
        }

    }
}

```

2. 与`@Import`进行配合使用

```java
package com.hzj.config;


import com.hzj.bean.Blue;
import com.hzj.bean.Color;
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
}
```