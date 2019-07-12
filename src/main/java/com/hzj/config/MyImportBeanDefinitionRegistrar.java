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
