# 集成swagger-ui

## 简介

一个无依赖的HTML、JS和CSS集合，可以为Swagger兼容API动态生成优雅文档。

## 引入依赖

```gradle
dependencies {
    // https://mvnrepository.com/artifact/io.springfox/springfox-swagger2
    compile group: 'io.springfox', name: 'springfox-swagger2', version: '2.7.0'
    // https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui
    compile group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.7.0'
}
```

## 编写配置类

```java
package com.hzj.bookshop.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Swagger配置类
 * @author hzj
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hzj.bookshop"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * 配置认证模式
     */
    private List<ApiKey> securitySchemes() {
        return newArrayList(new ApiKey("Authorization", "Authorization", "header"));
    }

    /**
     * 配置认证上下文
     */
    private List<SecurityContext> securityContexts() {
        return newArrayList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("Authorization", authorizationScopes));
    }

    /**
     * 项目信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("豆瓣读书项目 RESTful APIs")
                .version("1.0")
                .build();
    }
}
```

## 使用

### 注解

#### @Api

作用与类上,表示这个类是`swagger`的资源

tags: 表示说明

value: 也是说明,可以使用`tags`替代

#### @ApiOperation

用于方法,表示一个http请求

value: 用于方法描述

notes: 用于提示信息

#### @ApiParam

用于方法,参数,字段说明

name: 参数名
value: 参数说明
required: 是否必填

#### @ApiModel

用于类

value: 表示类对象

description: 表示描述

#### @ApiModelProperty

value: 字段说明
name: 重写属性名字
dataType: 重写属性类型
required: 是否必填
example: 举例说明
hidden: 隐藏

```java
package com.hzj.bookshop.request.body.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接收登录数据的实体对象
 * @author hzj
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "接收登录参数的实体")
public class Login {

    @ApiModelProperty(value = "账户", name = "account", required = true, example = "2962975318@qq.com")
    private String account;
    @ApiModelProperty(value = "密码", name = "password", required = true, example = "1234")
    private String password;

}
```

#### @ApiImplicitParam

用于方法,表示单独的请求参数

#### @ApiImplicitParams()

用于方法，包含多个 @ApiImplicitParam
name: 参数名称
value: 参数说明
dataType: 数据类型
paramType: 参数类型
example: 举例说明
