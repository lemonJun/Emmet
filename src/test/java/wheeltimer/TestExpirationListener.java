package wheeltimer;

import com.wheeltimer.ExpirationListener;

public class TestExpirationListener<E> implements ExpirationListener<E> {
    public void expired(E expireObject) {
        System.out.println(expireObject.toString() + " is expired at " + System.currentTimeMillis());
    }
}
