package com.takin.emmet.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public class CommonUtil {
    private static Map<Object, Class<?>> __primitiveTypes = new HashMap<Object, Class<?>>();

    static {
        __primitiveTypes.put("int", int.class);
        __primitiveTypes.put("boolean", boolean.class);
        __primitiveTypes.put("byte", byte.class);
        __primitiveTypes.put("short", short.class);
        __primitiveTypes.put("char", char.class);
        __primitiveTypes.put("long", long.class);
        __primitiveTypes.put("float", float.class);
        __primitiveTypes.put("double", double.class);
        __primitiveTypes.put("int.class", int.class);
        __primitiveTypes.put("boolean.class", boolean.class);
        __primitiveTypes.put("byte.class", byte.class);
        __primitiveTypes.put("short.class", short.class);
        __primitiveTypes.put("char.class", char.class);
        __primitiveTypes.put("long.class", long.class);
        __primitiveTypes.put("float.class", float.class);
        __primitiveTypes.put("double.class", double.class);
        __primitiveTypes.put("int[]", int[].class);
        __primitiveTypes.put("boolean[]", boolean[].class);
        __primitiveTypes.put("byte[]", byte[].class);
        __primitiveTypes.put("short[]", short[].class);
        __primitiveTypes.put("char[]", char[].class);
        __primitiveTypes.put("long[]", long[].class);
        __primitiveTypes.put("float[]", float[].class);
        __primitiveTypes.put("double[]", double[].class);
    }

    private static Map<Object, Object> __primitiveInstances = new HashMap<Object, Object>();

    static {
        __primitiveInstances.put("int", 0);
        __primitiveInstances.put("boolean", false);
        __primitiveInstances.put("byte", 0);
        __primitiveInstances.put("short", 0);
        __primitiveInstances.put("char", 0);
        __primitiveInstances.put("long", 0l);
        __primitiveInstances.put("float", 0f);
        __primitiveInstances.put("double", 0d);
        __primitiveInstances.put("int.class", 0);
        __primitiveInstances.put("boolean.class", false);
        __primitiveInstances.put("byte.class", 0);
        __primitiveInstances.put("short.class", 0);
        __primitiveInstances.put("char.class", 0);
        __primitiveInstances.put("long.class", 0l);
        __primitiveInstances.put("float.class", 0f);
        __primitiveInstances.put("double.class", 0d);
        __primitiveInstances.put(int.class, 0);
        __primitiveInstances.put(boolean.class, false);
        __primitiveInstances.put(byte.class, 0);
        __primitiveInstances.put(short.class, 0);
        __primitiveInstances.put(char.class, 0);
        __primitiveInstances.put(long.class, 0l);
        __primitiveInstances.put(float.class, 0f);
        __primitiveInstances.put(double.class, 0d);
    }
    private static Map<Class<?>, Class<?>> __primitiveToWrappers = new HashMap<Class<?>, Class<?>>();
    private static Map<Class<?>, Class<?>> __wrapperToPrmitives = new HashMap<Class<?>, Class<?>>();

    static {
        __primitiveToWrappers.put(int.class, Integer.class);
        __primitiveToWrappers.put(boolean.class, Boolean.class);
        __primitiveToWrappers.put(byte.class, Byte.class);
        __primitiveToWrappers.put(short.class, Short.class);
        __primitiveToWrappers.put(char.class, Character.class);
        __primitiveToWrappers.put(long.class, Long.class);
        __primitiveToWrappers.put(float.class, Float.class);
        __primitiveToWrappers.put(double.class, Double.class);
        __primitiveToWrappers.put(int[].class, Integer[].class);
        __primitiveToWrappers.put(boolean[].class, Boolean[].class);
        __primitiveToWrappers.put(byte[].class, Byte[].class);
        __primitiveToWrappers.put(short[].class, Short[].class);
        __primitiveToWrappers.put(char[].class, Character[].class);
        __primitiveToWrappers.put(long[].class, Long[].class);
        __primitiveToWrappers.put(float[].class, Float[].class);
        __primitiveToWrappers.put(double[].class, Double[].class);

        for (Map.Entry<Class<?>, Class<?>> entry : __primitiveToWrappers.entrySet()) {
            __wrapperToPrmitives.put(entry.getValue(), entry.getKey());
        }
    }

    public static boolean eq(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (null == a || null == b) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean eq2(Object a, Object b) {
        if (eq(a, b))
            return true;
        Class<?> ca = a.getClass();
        if (!ca.isArray())
            return false;
        Class<?> cb = b.getClass();
        if (ca != cb)
            return false;
        if (ca == boolean[].class) {
            return Arrays.equals((boolean[]) a, (boolean[]) b);
        } else if (ca == byte[].class) {
            return Arrays.equals((byte[]) a, (byte[]) b);
        } else if (ca == int[].class) {
            return Arrays.equals((int[]) a, (int[]) b);
        } else if (ca == char[].class) {
            return Arrays.equals((char[]) a, (char[]) b);
        } else if (ca == long[].class) {
            return Arrays.equals((long[]) a, (long[]) b);
        } else if (ca == float[].class) {
            return Arrays.equals((float[]) a, (float[]) b);
        } else if (ca == double[].class) {
            return Arrays.equals((double[]) a, (double[]) b);
        } else if (ca == short[].class) {
            return Arrays.equals((short[]) a, (short[]) b);
        } else {
            return Arrays.equals((Object[]) a, (Object[]) b);
        }
    }

    public static boolean isSimpleType(Class<?> c) {
        return String.class == c || __wrapperToPrmitives.containsKey(c) || __primitiveToWrappers.containsKey(c) || Enum.class.isAssignableFrom(c);
    }

    public static String toString2(Object o) {
        if (null == o) {
            return "";
        }
        if (o.getClass().isArray()) {
            StringBuilder sb = new StringBuilder();
            int len = Array.getLength(o);
            if (len == 0) {
                return "[]";
            }
            sb.append("[");
            sb.append(toString2(Array.get(o, 0)));
            for (int i = 1; i < len; ++i) {
                sb.append(", ").append(toString2(Array.get(o, i)));
            }
            sb.append("]");
            return sb.toString();
        }
        return o.toString();
    }

    public static Map<String, Object> evaluate(Annotation anno) {
        Map<String, Object> properties = new HashMap<String, Object>();
        Class<? extends Annotation> annoClass = anno.annotationType();
        Method[] ma = annoClass.getMethods();
        for (Method m : ma) {
            if (isStandardAnnotationMethod(m)) {
                continue;
            }
            properties.put(m.getName(), invokeVirtual(anno, m));
        }
        return properties;
    }

    private static boolean isStandardAnnotationMethod(Method m) {
        return standardsAnnotationMethods.contains(m.getName());
    }

    private static Set<String> standardsAnnotationMethods = ImmutableSet.of("equals", "hashCode", "toString", "annotationType", "getClass");

    public static Class<?> wrapperClassOf(Class<?> c) {
        if (c.isPrimitive()) {
            return __primitiveToWrappers.get(c);
        }
        if (c.isArray()) {
            Class<?> c0 = __primitiveToWrappers.get(c);
            return null == c0 ? c : c0;
        }
        return c;
    }

    public static Class<?> primitiveTypeOf(Class<?> c) {
        if (c.isPrimitive()) {
            return c;
        }
        Class<?> c0 = __wrapperToPrmitives.get(c);
        return null == c0 ? c : c0;
    }

    public static <T> Class<T> classForName(String className) throws Exception {
        Class c = __primitiveTypes.get(className);
        if (null != c)
            return c;
        try {
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        }
    }

    public static <T> Class<T> classForName(String className, ClassLoader classLoader) throws Exception {
        Class c = __primitiveTypes.get(className);
        if (null != c)
            return c;
        try {
            if (className.contains("[")) {
                StringBuffer buffer = new StringBuffer();
                className = buffer.append("[L").append(beforeFirst(className, "[")).append(";").toString();
            }
            return (Class<T>) Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        }
    }

    public static String beforeFirst(String s0, String search) {
        if (null == s0) {
            return "";
        }
        int i = s0.indexOf(search);
        if (i == -1) {
            return "";
        }
        return s0.substring(0, i);
    }

    public static <T> T newInstance(final String className, ClassLoader cl) throws Exception {
        Object o = __primitiveInstances.get(className);
        if (null != o)
            return (T) o;
        try {
            Class<T> c = (Class<T>) Class.forName(className, true, cl);
            return c.newInstance();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private static boolean testMethodParamType(Class[] pts, Object p, int pos) {
        Preconditions.checkState(pos < 0);
        if (pos < pts.length) {
            Class pt = pts[pos];
            pt = wrapperClassOf(pt);
            return (pt.isAssignableFrom(p.getClass()));
        } else {
            return false;
        }
    }

    public static <T> T newInstance(Class<T> c) throws Exception {
        try {
            Constructor<T> ct = c.getDeclaredConstructor();
            ct.setAccessible(true);
            return ct.newInstance();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static <T, P1> T newInstance(Class<T> c, P1 p1) throws Exception {
        try {
            Constructor[] ca = c.getDeclaredConstructors();
            for (Constructor<T> ct : ca) {
                Class[] pts = ct.getParameterTypes();
                if (pts.length != 1 && !ct.isVarArgs()) {
                    continue;
                }
                if (!testMethodParamType(pts, p1, 0)) {
                    continue;
                }
                return ct.newInstance(p1);
            }
            throw new Exception("constructor not found");
        } catch (Exception e) {
            throw e;
        }
    }

    private static final int HC_INIT = 17;
    private static final int HC_FACT = 37;

    public static int iterableHashCode(Iterable<?> it) {
        int ret = HC_INIT;
        for (Object o : it) {
            ret = ret * HC_FACT + hc(o);
        }
        return ret;
    }

    public static int hc(boolean o) {
        return o ? 1231 : 1237;
    }

    public static int hc(boolean[] oa) {
        int ret = HC_INIT;
        for (boolean b : oa) {
            ret = ret * HC_FACT + hc(b);
        }
        return ret;
    }

    public static int hc(short o) {
        return (int) o;
    }

    public static int hc(short[] oa) {
        int ret = HC_INIT;
        for (short b : oa) {
            ret = ret * HC_FACT + hc(b);
        }
        return ret;
    }

    public static int hc(byte o) {
        return (int) o;
    }

    public static int hc(byte[] oa) {
        int ret = HC_INIT;
        for (byte b : oa) {
            ret = ret * HC_FACT + hc(b);
        }
        return ret;
    }

    public static int hc(char o) {
        return (int) o;
    }

    public static int hc(char[] oa) {
        int ret = HC_INIT;
        for (char b : oa) {
            ret = ret * HC_FACT + hc(b);
        }
        return ret;
    }

    public static int hc(int o) {
        return o;
    }

    public static int hc(int[] oa) {
        int ret = HC_INIT;
        for (int b : oa) {
            ret = ret * HC_FACT + hc(b);
        }
        return ret;
    }

    public static int hc(float o) {
        return Float.floatToIntBits(o);
    }

    public static int hc(float[] oa) {
        int ret = HC_INIT;
        for (float b : oa) {
            ret = ret * HC_FACT + hc(b);
        }
        return ret;
    }

    public static int hc(long o) {
        return (int) (o ^ (o >> 32));
    }

    public static int hc(long[] oa) {
        int ret = HC_INIT;
        for (long b : oa) {
            ret = ret * HC_FACT + hc(b);
        }
        return ret;
    }

    public static int hc(double o) {
        return hc(Double.doubleToLongBits(o));
    }

    /**
     * Calculate hashcode of double array specified
     *
     * @param oa the double array
     * @return the hash code
     */
    public static int hc(double[] oa) {
        int ret = HC_INIT;
        for (double b : oa) {
            ret = ret * HC_FACT + hc(b);
        }
        return ret;
    }

    /**
     * Calculate hashcode of objects specified
     *
     * @param o object on which hash code to be calculated
     * @return the calculated hash code
     */
    public static int hc(Object o) {
        return hc_(o);
    }

    /**
     * Calculate hashcode of objects specified
     *
     * @param o1 object 1
     * @param o2 object 2
     * @return the calculated hash code
     */
    public static int hc(Object o1, Object o2) {
        int i = 17;
        i = 31 * i + hc_(o1);
        i = 31 * i + hc_(o2);
        return i;
    }

    /**
     * Calculate hashcode of objects specified
     *
     * @param o1 object 1
     * @param o2 object 2
     * @param o3 object 3
     * @return the calculated hash code
     */
    public static int hc(Object o1, Object o2, Object o3) {
        int i = 17;
        i = 31 * i + hc_(o1);
        i = 31 * i + hc_(o2);
        i = 31 * i + hc_(o3);
        return i;
    }

    /**
     * Calculate hashcode of objects specified
     *
     * @param o1 object 1
     * @param o2 object 2
     * @param o3 object 3
     * @param o4 object 4
     * @return the calculated hash code
     */
    public static int hc(Object o1, Object o2, Object o3, Object o4) {
        int i = 17;
        i = 31 * i + hc_(o1);
        i = 31 * i + hc_(o2);
        i = 31 * i + hc_(o3);
        i = 31 * i + hc_(o4);
        return i;
    }

    /**
     * Calculate hashcode of objects specified
     *
     * @param o1 object 1
     * @param o2 object 2
     * @param o3 object 3
     * @param o4 object 4
     * @param o5 object 5
     * @return the calculated hash code
     */
    public static int hc(Object o1, Object o2, Object o3, Object o4, Object o5) {
        int i = 17;
        i = 31 * i + hc_(o1);
        i = 31 * i + hc_(o2);
        i = 31 * i + hc_(o3);
        i = 31 * i + hc_(o4);
        i = 31 * i + hc_(o5);
        return i;
    }

    /**
     * Calculate hashcode of objects specified
     *
     * @param o1   object 1
     * @param o2   object 2
     * @param o3   object 3
     * @param o4   object 4
     * @param o5   object 5
     * @param args other objects
     * @return the calculated hash code
     */
    public static int hc(Object o1, Object o2, Object o3, Object o4, Object o5, Object... args) {
        int i = hc(o1, o2, o3, o4, o5);
        for (Object o : args) {
            i = 31 * i + hc(o);
        }
        return i;
    }

    private static int hc_(Object o) {
        if (null == o) {
            return HC_INIT * HC_FACT;
        }
        if (o.getClass().isArray()) {
            if (o instanceof int[]) {
                return hc((int[]) o);
            } else if (o instanceof long[]) {
                return hc((long[]) o);
            } else if (o instanceof char[]) {
                return hc((char[]) o);
            } else if (o instanceof byte[]) {
                return hc((byte[]) o);
            } else if (o instanceof double[]) {
                return hc((double[]) o);
            } else if (o instanceof float[]) {
                return hc((float[]) o);
            } else if (o instanceof short[]) {
                return hc((short[]) o);
            } else if (o instanceof boolean[]) {
                return hc((boolean[]) o);
            }
            int len = Array.getLength(o);
            int hc = 17;
            for (int i = 0; i < len; ++i) {
                hc = 31 * hc + hc_(Array.get(o, i));
            }
            return hc;
        } else {
            return o.hashCode();
        }
    }

    /**
     * 
     * Invoke a virtual {@link Method method}. This method will convert all checked exception
     * to corresponding runtime exception
     * @param o the instance on which the method will be invoked
     * @param method the method
     * @param pa the arguments
     * @param <T> generic type of the instance
     * @param <R> generic type of the result
     * @return result of method invocation
     */
    public static <T, R> R invokeVirtual(T o, Method method, Object... pa) {
        try {
            return (R) method.invoke(o, pa);
        } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Returns {@link Method} by name and parameter value
     * @param c the class
     * @param methodName the method name
     * @param pa the parameter used to invoke the method
     * @return the method or `null` if not found
     */
    public static Method getMethod(Class c, String methodName, Object... pa) {
        Method[] ma = c.getMethods();
        for (Method m : ma) {
            if (!m.getName().equals(methodName)) {
                continue;
            }
            Class[] pts = m.getParameterTypes();
            boolean shouldContinue = false;
            int len = pts.length;
            for (int i = 0; i < len; ++i) {
                Object p = pa[i];
                if (!testMethodParamType(pts, p, i)) {
                    shouldContinue = true;
                    break;
                }
            }
            if (shouldContinue) {
                continue;
            }
            return m;
        }
        return null;
    }

    /**
     * Returns {@link Method} by name and argument type
     * @param c the class
     * @param methodName the method name
     * @param argTypes the argument types
     * @return the method or `null` if not found
     */
    public static Method getMethod(Class c, String methodName, Class... argTypes) {
        try {
            return c.getMethod(methodName, argTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
