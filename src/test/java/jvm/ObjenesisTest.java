package jvm;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

public class ObjenesisTest {

    public static void main(String[] args) {
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator instantiator = objenesis.getInstantiatorOf(java.sql.Date.class);

        java.sql.Date date = (java.sql.Date) instantiator.newInstance();
        System.out.println(date);
    }
}
