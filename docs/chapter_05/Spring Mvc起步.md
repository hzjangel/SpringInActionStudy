# Spring Mvc起步

## 初始化一个Spring Mvc项目

### 配置DispatcherServlet
```java
package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import spittr.web.WebConfig;

/**
* 配置DispatcherServlet
* 
*/
public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] { RootConfig.class };
  }

  /**
  * 指定配置类
  */
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] { WebConfig.class };
  }

  /*
  * 将DispatcherServlet映射到"/"
  * 它会处理进入应用的所有请求
  */
  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }

}
```

### 指定配置类编写
```java
package spittr.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//启动Spring MVC
@EnableWebMvc
//启动组件扫描
@ComponentScan("spittr")
public class WebConfig extends WebMvcConfigurerAdapter {

  /**
   * 配置jsp视图解析器
   * @return
   */
  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }

  /**
   * 配置静态资源处理
   * @param configurer
   */
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }
}

```

### 编写Dispatcher映射
```java
package spittr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages={"spittr"}, 
    excludeFilters={
        @Filter(type=FilterType.ANNOTATION, value= EnableWebMvc.class)
    })
public class RootConfig {
}

```

### 编写一个控制器，作为测试
```java
package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

  @RequestMapping(method = GET)
  public String home(Model model) {
    return "home";
  }
}
```
 在WEB-INF/views下新建home.jsp，启动程序进行测试
 
需要的jar包的配置文件
```gradle
dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.11'
    testCompile group: 'junit', name: 'junit', version: '4.12'
    testCompile "junit:junit-dep:$junitVersion"
    testCompile "org.springframework:spring-test:$springVersion"
    testCompile "org.mockito:mockito-core:$mockitoVersion"
    testCompile "org.hamcrest:hamcrest-library:$hamcrestVersion"

    compile "javax.servlet:jstl:$jstlVersion"
    compile group: 'org.springframework', name: 'spring-core', version: '4.3.2.RELEASE'
    compile group: 'org.springframework', name: 'spring-context', version: '4.3.2.RELEASE'
    compile group: 'org.springframework', name: 'spring-beans', version: '4.3.2.RELEASE'
    compile group: 'org.springframework', name: 'spring-expression', version: '4.3.2.RELEASE'
    compile group: 'org.springframework', name: 'spring-web', version: '4.3.2.RELEASE'
    compile group: 'org.springframework', name: 'spring-webmvc', version: '4.3.2.RELEASE'
    compile group: 'org.apache.tomcat', name: 'tomcat-servlet-api', version: '9.0.13'
    compile group: 'log4j', name: 'log4j', version: '1.2.17'
}
```
`gradle.properties`
```gradle
activeMQVersion=5.7.0
aspectJVersion=1.7.2
commonsLangVersion = 3.1
ehcacheVersion=2.7.4
ehcacheJCacheVersion=1.4.0-beta1
h2Version=1.4.182
hamcrestVersion = 1.3
hibernateVersion=4.1.6.Final
hibernateEntityManagerVersion=4.0.1.Final
hibernateValidatorVersion = 5.0.1.Final
jacksonVersion=2.4.3
javaxMailVersion=1.4.7
jspApiVersion = 2.1
jspElVersion = 2.2.4
jstlVersion = 1.2
junitVersion=4.11
log4jVersion=1.2.14
mockitoVersion=1.9.5
servletApiVersion = 3.1.0
slf4jVersion = 1.7.5
springAMQPVersion=1.0.0.RELEASE
springDataJpaVersion=1.3.2.RELEASE
springSecurityVersion = 3.2.0.RELEASE
springVersion=4.0.7.RELEASE
springWebflowVersion=2.4.1.RELEASE
systemRulesVersion=1.5.0
thymeleafVersion = 2.1.3.RELEASE
tilesVersion = 3.0.1
```
