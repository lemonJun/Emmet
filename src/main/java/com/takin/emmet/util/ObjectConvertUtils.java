package com.takin.emmet.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.takin.emmet.annotation.FieldMapper;
import com.takin.emmet.string.StringUtil;

/**
 * 类转换工具
 * 
 * @author sunlingao@58.com
 * @date 2016年7月24日
 * @desc
 */
public class ObjectConvertUtils {

    private final static Logger logger = LoggerFactory.getLogger(ObjectConvertUtils.class);

    private final static String GET = "get";
    private final static String SET = "set";

    /**
     * <p>
     * ex:实体需有get set方法.(属性名首字母大写)<br>
     * <h1>如果2个实体字段,类型一样,不需要添加注解</h1>
     * <h1>注解:FieldMapper</h1><br>
     * <p>1.name 别名  src 中的age 需映射到target的 age1字段  <br>
     * \@FieldMapper(name = "age1")</p>
     * <p>2.isMapper 字段是否参与映射 默认为true<br>
     * \@FieldMapper(isMapper = false)
     * </p>
     * <p>3.mapperCls 映射字段的类型 src 中的time是long类型 target的time是date类型,<br>具体
     * 的转换逻辑需要在src的getTime方法中实现 而且返回类型一定是date<br>
     *\@FieldMapper(mapperCls=Date.class)
     * </p>
     * 
     * </p>
     * 
     * @param srcs 多个数据源
     * @param target 待生成的实体
     * @return T
     */
    public static <T> T mapperObject(Class<T> target, Object... srcs) {
        if (srcs == null || srcs.length == 0)
            return null;
        for (Object obj : srcs) {
            if (obj == null)
                return null;
        }
        T tg = null;
        try {
            tg = target.newInstance();
            for (Object obj : srcs) {
                mapperObjectField(obj, tg);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("convert ex", e);
        }
        return tg;
    }

    private static void mapperObjectField(Object src, Object target) {
        try {
            Field[] fields = src.getClass().getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    String name = field.getName();
                    try {
                        Method s = src.getClass().getDeclaredMethod(GET + StringUtil.capitalize(name));
                        Object value = s.invoke(src);
                        FieldMapper mapper = field.getAnnotation(FieldMapper.class);
                        boolean flag = true;
                        if (mapper != null) {
                            name = StringUtil.isNotNullOrEmpty(mapper.name()) ? mapper.name() : name;
                            flag = mapper.isMapper();
                        }
                        if (flag && value != null) {
                            Class<?> cls = (mapper == null) ? Object.class : mapper.mapperCls();
                            Method t = target.getClass().getDeclaredMethod(SET + StringUtil.capitalize(name), (cls.getName().equals(Object.class.getName())) ? field.getType() : cls);
                            t.invoke(target, value);
                        }
                    } catch (NoSuchMethodException
                                    | InvocationTargetException e) {
                        logger.debug(String.format("noSuchMethod or setValue error method is set/get%s and type is %s", StringUtil.capitalize(name), field.getType()));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("convert ex", e);
        }
    }

}
