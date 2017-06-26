package com.reflect;

import com.esotericsoftware.reflectasm.MethodAccess;

public class ReflectAsmTest {
    public static void main(String[] args) {
        User user = new User();
        //使用reflectasm生产User访问类  
        MethodAccess access = MethodAccess.get(User.class);
        //invoke setName方法name值  
        access.invoke(user, "setName", "张三");
        //invoke getName方法 获得值  
        String name = (String) access.invoke(user, "getName", new Class[] { int.class }, new Object[] { 1 });
        System.out.println(name);
    }
}
