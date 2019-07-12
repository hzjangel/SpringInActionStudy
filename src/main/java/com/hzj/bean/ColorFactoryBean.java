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
