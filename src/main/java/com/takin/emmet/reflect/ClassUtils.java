package com.takin.emmet.reflect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * ClassUtils. (Tool, Static, ThreadSafe)
 */
public class ClassUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    public static final String CLASS_EXTENSION = ".class";

    public static final String JAVA_EXTENSION = ".java";

    /**
     * Return all interfaces that the given class implements as array,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * @param clazz the class to analyze for interfaces
     * @return all interfaces that the given object implements as array
     */
    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz) {
        return getAllInterfacesForClass(clazz, null);
    }

    /**
     * Return all interfaces that the given class implements as array,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * @param clazz the class to analyze for interfaces
     * @param classLoader the ClassLoader that the interfaces need to be visible in
     * (may be <code>null</code> when accepting all declared interfaces)
     * @return all interfaces that the given object implements as array
     */
    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz, ClassLoader classLoader) {
        Set<Class> ifcs = getAllInterfacesForClassAsSet(clazz, classLoader);
        return ifcs.toArray(new Class[ifcs.size()]);
    }

    /**
     * Return all interfaces that the given class implements as Set,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * @param clazz the class to analyze for interfaces
     * @param classLoader the ClassLoader that the interfaces need to be visible in
     * (may be <code>null</code> when accepting all declared interfaces)
     * @return all interfaces that the given object implements as Set
     */
    @SuppressWarnings("rawtypes")
    public static Set<Class> getAllInterfacesForClassAsSet(Class clazz, ClassLoader classLoader) {
        Preconditions.checkNotNull(clazz, "Class must not be null");
        if (clazz.isInterface() && isVisible(clazz, classLoader)) {
            return Collections.singleton(clazz);
        }
        Set<Class> interfaces = new LinkedHashSet<>();
        Class sclazz = clazz;
        while (sclazz != null) {
            Class<?>[] ifcs = sclazz.getInterfaces();
            for (Class<?> ifc : ifcs) {
                interfaces.addAll(getAllInterfacesForClassAsSet(ifc, classLoader));
            }
            sclazz = clazz.getSuperclass();
        }
        return interfaces;
    }

    /**
     * Check whether the given class is visible in the given ClassLoader.
     * @param clazz the class to check (typically an interface)
     * @param classLoader the ClassLoader to check against (may be <code>null</code>,
     * in which case this method will always return <code>true</code>)
     */
    public static boolean isVisible(Class<?> clazz, ClassLoader classLoader) {
        if (classLoader == null) {
            return true;
        }
        try {
            Class<?> actualClass = classLoader.loadClass(clazz.getName());
            return (clazz == actualClass);
            // Else: different interface class found...
        } catch (ClassNotFoundException ex) {
            // No interface class found...
            return false;
        }
    }

    public static Object newInstance(String name) {
        try {
            return forName(name).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static Class<?> forName(String[] packages, String className) {
        try {
            return _forName(className);
        } catch (ClassNotFoundException e) {
            if (packages != null && packages.length > 0) {
                for (String pkg : packages) {
                    try {
                        return _forName(pkg + "." + className);
                    } catch (ClassNotFoundException e2) {
                        continue;
                    }
                }
            }
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static Class<?> forName(String className) {
        try {
            return _forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static Class<?> _forName(String className) throws ClassNotFoundException {
        if ("boolean".equals(className))
            return boolean.class;
        if ("byte".equals(className))
            return byte.class;
        if ("char".equals(className))
            return char.class;
        if ("short".equals(className))
            return short.class;
        if ("int".equals(className))
            return int.class;
        if ("long".equals(className))
            return long.class;
        if ("float".equals(className))
            return float.class;
        if ("double".equals(className))
            return double.class;
        if ("boolean[]".equals(className))
            return boolean[].class;
        if ("byte[]".equals(className))
            return byte[].class;
        if ("char[]".equals(className))
            return char[].class;
        if ("short[]".equals(className))
            return short[].class;
        if ("int[]".equals(className))
            return int[].class;
        if ("long[]".equals(className))
            return long[].class;
        if ("float[]".equals(className))
            return float[].class;
        if ("double[]".equals(className))
            return double[].class;
        try {
            return arrayForName(className);
        } catch (ClassNotFoundException e) {
            if (className.indexOf('.') == -1) { // 尝试java.lang包
                try {
                    return arrayForName("java.lang." + className);
                } catch (ClassNotFoundException e2) {
                    // 忽略尝试异常, 抛出原始异常
                }
            }
            throw e;
        }
    }

    private static Class<?> arrayForName(String className) throws ClassNotFoundException {
        return Class.forName(className.endsWith("[]") ? "[L" + className.substring(0, className.length() - 2) + ";" : className, true, Thread.currentThread().getContextClassLoader());
    }

    public static Class<?> getBoxedClass(Class<?> type) {
        if (type == boolean.class) {
            return Boolean.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == short.class) {
            return Short.class;
        } else if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == double.class) {
            return Double.class;
        } else {
            return type;
        }
    }

    public static Boolean boxed(boolean v) {
        return Boolean.valueOf(v);
    }

    public static Character boxed(char v) {
        return Character.valueOf(v);
    }

    public static Byte boxed(byte v) {
        return Byte.valueOf(v);
    }

    public static Short boxed(short v) {
        return Short.valueOf(v);
    }

    public static Integer boxed(int v) {
        return Integer.valueOf(v);
    }

    public static Long boxed(long v) {
        return Long.valueOf(v);
    }

    public static Float boxed(float v) {
        return Float.valueOf(v);
    }

    public static Double boxed(double v) {
        return Double.valueOf(v);
    }

    public static Object boxed(Object v) {
        return v;
    }

    public static boolean unboxed(Boolean v) {
        return v == null ? false : v.booleanValue();
    }

    public static char unboxed(Character v) {
        return v == null ? '\0' : v.charValue();
    }

    public static byte unboxed(Byte v) {
        return v == null ? 0 : v.byteValue();
    }

    public static short unboxed(Short v) {
        return v == null ? 0 : v.shortValue();
    }

    public static int unboxed(Integer v) {
        return v == null ? 0 : v.intValue();
    }

    public static long unboxed(Long v) {
        return v == null ? 0 : v.longValue();
    }

    public static float unboxed(Float v) {
        return v == null ? 0 : v.floatValue();
    }

    public static double unboxed(Double v) {
        return v == null ? 0 : v.doubleValue();
    }

    public static Object unboxed(Object v) {
        return v;
    }

    public static boolean isNotEmpty(Object object) {
        return getSize(object) > 0;
    }

    public static int getSize(Object object) {
        if (object == null) {
            return 0;
        }
        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).size();
        } else if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).size();
        } else if (object.getClass().isArray()) {
            return Array.getLength(object);
        } else {
            return -1;
        }
    }

    public static URI toURI(String name) {
        try {
            return new URI(name);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getGenericClass(Class<?> cls) {
        return getGenericClass(cls, 0);
    }

    public static Class<?> getGenericClass(Class<?> cls, int i) {
        try {
            ParameterizedType parameterizedType = ((ParameterizedType) cls.getGenericInterfaces()[0]);
            Object genericClass = parameterizedType.getActualTypeArguments()[i];
            if (genericClass instanceof ParameterizedType) { // 处理多级泛型
                return (Class<?>) ((ParameterizedType) genericClass).getRawType();
            } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
                return (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
            } else if (genericClass != null) {
                return (Class<?>) genericClass;
            }
        } catch (Exception e) {
        }
        if (cls.getSuperclass() != null) {
            return getGenericClass(cls.getSuperclass(), i);
        } else {
            throw new IllegalArgumentException(cls.getName() + " generic type undefined!");
        }
    }

    public static boolean isBeforeJava5(String javaVersion) {
        return (javaVersion == null || javaVersion.length() == 0 || "1.0".equals(javaVersion) || "1.1".equals(javaVersion) || "1.2".equals(javaVersion) || "1.3".equals(javaVersion) || "1.4".equals(javaVersion));
    }

    public static boolean isBeforeJava6(String javaVersion) {
        return isBeforeJava5(javaVersion) || "1.5".equals(javaVersion);
    }

    public static String toString(Throwable e) {
        StringWriter w = new StringWriter();
        PrintWriter p = new PrintWriter(w);
        p.print(e.getClass().getName() + ": ");
        if (e.getMessage() != null) {
            p.print(e.getMessage() + "\n");
        }
        p.println();
        try {
            e.printStackTrace(p);
            return w.toString();
        } finally {
            p.close();
        }
    }

    private static final int JIT_LIMIT = 5 * 1024;

    public static void checkBytecode(String name, byte[] bytecode) {
        if (bytecode.length > JIT_LIMIT) {
            logger.error("The template bytecode too long, may be affect the JIT compiler. template class: " + name);
        }
    }

    public static String getInitCode(Class<?> type) {
        if (byte.class.equals(type) || short.class.equals(type) || int.class.equals(type) || long.class.equals(type) || float.class.equals(type) || double.class.equals(type)) {
            return "0";
        } else if (char.class.equals(type)) {
            return "'\\0'";
        } else if (boolean.class.equals(type)) {
            return "false";
        } else {
            return "null";
        }
    }

    public static <K, V> Map<K, V> toMap(Map.Entry<K, V>[] entries) {
        Map<K, V> map = new HashMap<>();
        if (entries != null && entries.length > 0) {
            for (Map.Entry<K, V> enrty : entries) {
                map.put(enrty.getKey(), enrty.getValue());
            }
        }
        return map;
    }

    private ClassUtils() {
    }

}
