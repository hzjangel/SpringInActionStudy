package com.hzj.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author hzj
 */
public class MyImportSelect implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.hzj.bean.Yellow", "com.hzj.bean.Red"};
    }
}
