package com.takin.emmet.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class AnnotationUtil {

    /**
     * 获取一个类所拥用的某个注解的集合
     * @param c 
     * @param annoClass 
     * @return
     */
    public static <T extends Annotation> T declaredAnnotation(Class<?> c, Class<T> annoClass) {
        Annotation[] aa = c.getDeclaredAnnotations();
        if (null == aa) {
            return null;
        }
        for (Annotation a : aa) {
            if (annoClass.isInstance(a)) {
                return (T) a;
            }
        }
        return null;
    }

    /**
     * Returns the {@link Annotation} tagged on another annotation instance
     *
     * @param annotation the annotation instance
     * @param tagClass   the expected annotation class
     * @param <T>        the generic type of the expected annotation
     * @return the annotation tagged on annotation of type `tagClass`
     */
    public static <T extends Annotation> T tagAnnotation(Annotation annotation, Class<T> tagClass) {
        Class<?> c = annotation.annotationType();
        for (Annotation a : c.getAnnotations()) {
            if (tagClass.isInstance(a)) {
                return (T) a;
            }
        }
        return null;
    }

    /**
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T createAnnotation(final Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz, Annotation.class }, new SimpleAnnoInvocationHandler(clazz));
    }

    /**
     * <p>Generate a hash code for the given annotation using the algorithm
     * presented in the {@link Annotation#hashCode()} API docs.</p>
     *
     * @param a the Annotation for a hash code calculation is desired, not
     *          {@code null}
     * @return the calculated hash code
     * @throws RuntimeException      if an {@code Exception} is encountered during
     *                               annotation member access
     * @throws IllegalStateException if an annotation method invocation returns
     *                               {@code null}
     */
    public static int hashCode(Annotation a) {
        int result = 0;
        Class<? extends Annotation> type = a.annotationType();
        for (Method m : type.getDeclaredMethods()) {
            try {
                Object value = m.invoke(a);
                if (value == null) {
                    throw new IllegalStateException(String.format("Annotation method %s returned null", m));
                }
                result += hashMember(m.getName(), value);
            } catch (RuntimeException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return result;
    }

    /**
     * Returns {@code true} if the given annotation type has no attributes.
     */
    public static boolean isMarker(Class<? extends Annotation> annotationType) {
        return annotationType.getDeclaredMethods().length == 0;
    }

    public static boolean isAllDefaultMethods(Class<? extends Annotation> annotationType) {
        boolean hasMethods = false;
        for (Method m : annotationType.getDeclaredMethods()) {
            hasMethods = true;
            if (m.getDefaultValue() == null) {
                return false;
            }
        }
        return hasMethods;
    }

    private static final LoadingCache<Class<? extends Annotation>, Annotation> cache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<? extends Annotation>, Annotation>() {
        @Override
        public Annotation load(Class<? extends Annotation> input) {
            return generateAnnotationImpl(input);
        }
    });

    /**
     * Generates an Annotation for the annotation class. Requires that the annotation is all
     * optionals.
     */
    public static <T extends Annotation> T generateAnnotation(Class<T> annotationType) {
        Preconditions.checkState(isAllDefaultMethods(annotationType), "%s is not all default methods", annotationType);
        return (T) cache.getUnchecked(annotationType);
    }

    private static <T extends Annotation> T generateAnnotationImpl(final Class<T> annotationType) {
        final Map<String, Object> members = resolveMembers(annotationType);
        return annotationType.cast(Proxy.newProxyInstance(annotationType.getClassLoader(), new Class<?>[] { annotationType }, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
                String name = method.getName();
                if (name.equals("annotationType")) {
                    return annotationType;
                } else if (name.equals("toString")) {
                    return annotationToString(annotationType, members);
                } else if (name.equals("hashCode")) {
                    return annotationHashCode(annotationType, members);
                } else if (name.equals("equals")) {
                    return annotationEquals(annotationType, members, args[0]);
                } else {
                    return members.get(name);
                }
            }
        }));
    }

    private static ImmutableMap<String, Object> resolveMembers(Class<? extends Annotation> annotationType) {
        ImmutableMap.Builder<String, Object> result = ImmutableMap.builder();
        for (Method method : annotationType.getDeclaredMethods()) {
            result.put(method.getName(), method.getDefaultValue());
        }
        return result.build();
    }

    /** Implements {@link Annotation#equals}. */
    private static boolean annotationEquals(Class<? extends Annotation> type, Map<String, Object> members, Object other) throws Exception {
        if (!type.isInstance(other)) {
            return false;
        }
        for (Method method : type.getDeclaredMethods()) {
            String name = method.getName();
            if (!Arrays.deepEquals(new Object[] { method.invoke(other) }, new Object[] { members.get(name) })) {
                return false;
            }
        }
        return true;
    }

    /** Implements {@link Annotation#hashCode}. */
    private static int annotationHashCode(Class<? extends Annotation> type, Map<String, Object> members) throws Exception {
        int result = 0;
        for (Method method : type.getDeclaredMethods()) {
            String name = method.getName();
            Object value = members.get(name);
            result += (127 * name.hashCode()) ^ (Arrays.deepHashCode(new Object[] { value }) - 31);
        }
        return result;
    }

    private static final MapJoiner JOINER = Joiner.on(", ").withKeyValueSeparator("=");

    private static final Function<Object, String> DEEP_TO_STRING_FN = new Function<Object, String>() {
        @Override
        public String apply(Object arg) {
            String s = Arrays.deepToString(new Object[] { arg });
            return s.substring(1, s.length() - 1); // cut off brackets
        }
    };

    /** Implements {@link Annotation#toString}. */
    private static String annotationToString(Class<? extends Annotation> type, Map<String, Object> members) throws Exception {
        StringBuilder sb = new StringBuilder().append("@").append(type.getName()).append("(");
        JOINER.appendTo(sb, Maps.transformValues(members, DEEP_TO_STRING_FN));
        return sb.append(")").toString();
    }

    /**
     * Returns true if the given annotation is retained at runtime.
     */
    public static boolean isRetainedAtRuntime(Class<? extends Annotation> annotationType) {
        Retention retention = annotationType.getAnnotation(Retention.class);
        return retention != null && retention.value() == RetentionPolicy.RUNTIME;
    }

    static boolean containsComponentAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            // TODO(user): Should we scope this down to dagger.Component?
            if (annotation.annotationType().getSimpleName().equals("Component")) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks for the presence of annotations. Caches results because Android doesn't.
     */
    static class AnnotationChecker {
        private final Collection<Class<? extends Annotation>> annotationTypes;

        /** Returns true if the given class has one of the desired annotations. */
        private CacheLoader<Class<? extends Annotation>, Boolean> hasAnnotations = new CacheLoader<Class<? extends Annotation>, Boolean>() {
            public Boolean load(Class<? extends Annotation> annotationType) {
                for (Annotation annotation : annotationType.getAnnotations()) {
                    if (annotationTypes.contains(annotation.annotationType())) {
                        return true;
                    }
                }
                return false;
            }
        };

        final LoadingCache<Class<? extends Annotation>, Boolean> cache = CacheBuilder.newBuilder().weakKeys().build(hasAnnotations);

        /**
         * Constructs a new checker that looks for annotations of the given types.
         */
        AnnotationChecker(Collection<Class<? extends Annotation>> annotationTypes) {
            this.annotationTypes = annotationTypes;
        }

        /**
         * Returns true if the given type has one of the desired annotations.
         */
        boolean hasAnnotations(Class<? extends Annotation> annotated) {
            return cache.getUnchecked(annotated);
        }
    }

    /**
     * Helper method for generating a hash code for a member of an annotation.
     *
     * @param name  the name of the member
     * @param value the value of the member
     * @return a hash code for this member
     */
    public static int hashMember(String name, Object value) {
        int part1 = name.hashCode() * 127;
        if (value.getClass().isArray()) {
            return part1 ^ arrayMemberHash(value.getClass().getComponentType(), value);
        }
        if (value instanceof Annotation) {
            return part1 ^ hashCode((Annotation) value);
        }
        return part1 ^ value.hashCode();
    }

    /**
     * Helper method for generating a hash code for an array.
     *
     * @param componentType the component type of the array
     * @param o             the array
     * @return a hash code for the specified array
     */
    private static int arrayMemberHash(Class<?> componentType, Object o) {
        if (componentType.equals(Byte.TYPE)) {
            return Arrays.hashCode((byte[]) o);
        }
        if (componentType.equals(Short.TYPE)) {
            return Arrays.hashCode((short[]) o);
        }
        if (componentType.equals(Integer.TYPE)) {
            return Arrays.hashCode((int[]) o);
        }
        if (componentType.equals(Character.TYPE)) {
            return Arrays.hashCode((char[]) o);
        }
        if (componentType.equals(Long.TYPE)) {
            return Arrays.hashCode((long[]) o);
        }
        if (componentType.equals(Float.TYPE)) {
            return Arrays.hashCode((float[]) o);
        }
        if (componentType.equals(Double.TYPE)) {
            return Arrays.hashCode((double[]) o);
        }
        if (componentType.equals(Boolean.TYPE)) {
            return Arrays.hashCode((boolean[]) o);
        }
        return Arrays.hashCode((Object[]) o);
    }
}
