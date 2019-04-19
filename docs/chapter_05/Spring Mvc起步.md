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

```test
springmvc的拦截器实现HandlerInterceptor接口后，会有三个抽象方法需要实现，分别为方法前执行preHandle，方法后postHandle，页面渲染后afterCompletion。

1、当俩个拦截器都实现放行操作时，顺序为preHandle 1，preHandle 2，postHandle 2，postHandle 1，afterCompletion 2，afterCompletion 1

2、当第一个拦截器preHandle返回false，也就是对其进行拦截时，第二个拦截器是完全不执行的，第一个拦截器只执行preHandle部分。

3、当第一个拦截器preHandle返回true，第二个拦截器preHandle返回false，顺序为preHandle 1，preHandle 2 ，afterCompletion 1


```

## Spring Mvc实现文件上传

### 配置multipart解析器

DispatcherServlet并没有实现任何解析multipart请求数据的功能。 它将该任务委托给了Spring MultipartResolver策略接口的实现， 通过这个实现类来解析multipart请求中的内容。 从Spring 3.1开始，Spring内置了两个MultipartResolver的实现供我们选择：

CommonsMultipartResolver：需要Jakarta Commons FileUpload解析multipart请求，依赖与于其他项目，可部署到Servlet 3.0之前的容器中，或者是Spring3.1之前的版本，不推荐使用

StandardServletMultipartResolver：依赖与Servlet3.0对multipart请求的支持（始于Spring3.1），推荐使用

### 使用Servlet3.0解析multipart请求

1. 在`WebConfig`中声明StandardServletMultipartResolver的bean

```java
  /**
   * 使用Servlet3.0解析multipart请求
   * @return
   * @throws IOException
   */
  @Bean
  public MultipartResolver multipartResolver() throws IOException {
    return new StandardServletMultipartResolver();
  }

```

2. 设置上传文件的大小，所写入文件的临时路径、请求大小

在`SpitterWebInitializer`中重写`customizeRegistration`方法，设置相应的内容

```java
public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected void customizeRegistration(Dynamic registration) {
    //设置临时文件为/tmp/spittr/uploads, 文件大小不超过2Mb,整个请求不超过4MB,所有文件都要写到磁盘中
    //记住,是磁盘中,所以/tmp/spittr/uploads是磁盘文件目录,在终端中可以cd进入/tmp/spittr/uploads目录
    registration.setMultipartConfig(
            new MultipartConfigElement("/tmp/spittr/uploads", 2097152, 4194304, 0)
    );
    super.customizeRegistration(registration);
  }
}
```

### 编写文件上传的控制器

先写一个简单的get请求跳转到文件上传是页面

```java
@Controller
@RequestMapping("/fileUploadController")
public class FileUploadController {
    @RequestMapping(method = RequestMethod.GET)
    public String uploadForm() {
        return "uploadForm";
    }


}
```

编写文件上传页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form method="POST" action="/fileUploadController" enctype="multipart/form-data">
    <input type="file" name="file"/><br/>
    <input type="submit"/>
</form>
</body>
</html>
```

编写上传文件接受的请求

```java
@Controller
@RequestMapping("/fileUploadController")
public class FileUploadController {
    @RequestMapping(method = RequestMethod.POST)
    public String processUpload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());
        //这里的/tmp/spittr/是相对磁盘的路径，进入磁盘的根目录，在进入temp下的spittr文件夹下查看上传的结果
        file.transferTo(new File("/tmp/spittr/" + file.getOriginalFilename()));
        return "redirect:/";
    }


}
```

