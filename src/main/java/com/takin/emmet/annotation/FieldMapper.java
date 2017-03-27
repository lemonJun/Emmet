package com.takin.emmet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段映射注解
 * @author sunlingao@58.com
 * @date 2016年7月24日
 * @desc
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMapper {

    /**
     * 别名字段
     * <p>ex:age需映射到另一个实体maxAge上  name='maxAge'</p>
     * @author:sunlingao@58.com
     * @date:2016年7月29日
     * @return
     * String
     */
    String name() default "";

    /**
     * 是否参与映射
     * @author:sunlingao@58.com
     * @date:2016年7月29日
     * @return
     * boolean
     */
    boolean isMapper() default true;

    /**
     * 需要映射的属性类型
     * @return
     * Class<?>
     */
    Class<?> mapperCls() default Object.class;
}
