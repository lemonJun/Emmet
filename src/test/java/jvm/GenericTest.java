package jvm;

import java.util.ArrayList;

import com.google.common.reflect.TypeToken;

public class GenericTest {
    public static void main(String[] args) {
        ArrayList<String> arrayList1 = new ArrayList<String>();
        arrayList1.add("abc");
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(123);
        System.out.println(arrayList1.getClass() == arrayList2.getClass());

        TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>() {
        };
        TypeToken<?> genericTypeToken = typeToken.resolveType(ArrayList.class.getTypeParameters()[0]);
        System.out.println(genericTypeToken.getType());
        System.out.println(genericTypeToken.getRawType());
        System.out.println(genericTypeToken.getSubtype(String.class));
        System.out.println(genericTypeToken.getTypes());

    }
}
