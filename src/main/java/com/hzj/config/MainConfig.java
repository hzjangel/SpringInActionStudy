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


    /**
     * prototype: 多实例: ioc容器启动并调用方法创建对象放在容器中,每次获取的时候才会调用方法创建对象
     * singleton: 单实例(默认值),IOC容器启动会调用方法创建对象放到IOC容器中，以后每次获取就是从容器(map.get())中拿
     * request: 同一次请求创建一次实例(web环境,用的不多)
     * session: 同一次session创建一次实例(web环境,用的不多)
     * @return
     */
    @Scope("singleton")
    @Lazy
    //@Scope("singleton") = @Scope
    @Bean("person")
    public Person person() {
        return new Person("hzj", 18);
    }


    /**
     * @return
     */
    //@Conditional：按照一定的条件判断，满足条件给容器中注册bean
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person1() {
        return new Person("bill Gates", 62);
    }


    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person2() {
        return new Person("linus", 48);
    }


    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
}
