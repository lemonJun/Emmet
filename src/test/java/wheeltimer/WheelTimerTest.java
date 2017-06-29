package wheeltimer;

import java.util.concurrent.TimeUnit;

import com.wheeltimer.WheelTimer;

public class WheelTimerTest {

    public static void main(String[] args) throws Exception {
        WheelTimer<Integer> wheelTimer = new WheelTimer<Integer>(20, TimeUnit.SECONDS, 60);
        wheelTimer.addExpirationListener(new TestExpirationListener<Integer>());

        wheelTimer.start();
        //        Thread.sleep(10000L);
        System.out.println("start time " + System.currentTimeMillis() / 1000);
        wheelTimer.add(20);

    }
}
