package jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Date;

public class TestReflect {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void testSet(Long times) {
        TestReflect po = new TestReflect();
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            po.setAddress("xxxx");
        }
        System.out.println("set");
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void testMethodHandle(Long times) throws Throwable {
        TestReflect po = new TestReflect();
        MethodHandle handle = MethodHandles.lookup().findVirtual(TestReflect.class, "setAddress", MethodType.methodType(void.class, String.class));
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            //            handle.invoke(po, "xxxx");
            handle.invokeExact(po, "xxxx");
        }

        System.out.println("methodhandle");
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void testReflect(Long times) throws Exception {
        TestReflect po = new TestReflect();
        Method m = po.getClass().getMethod("setAddress", String.class);
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            m.invoke(po, "xxxx");
        }
        System.out.println("reflect");
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void main(String[] args) throws Throwable {
        Long times = 10000 * 10000l;
        testSet(times); // -Xcomp:0.5k ms  -Xint: 3k ms
        testReflect(times); // -Xcomp:2k ms -Xint: ms
        testMethodHandle(times); // -Xcomp:10k ms   -Xint:
    }

}
