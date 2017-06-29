package wheeltimer;

import com.wheeltimer.WaitStrategy;

public class TimerWithSleepWait extends AbstractTimerTest {
    @Override
    public WaitStrategy waitStrategy() {
        return new WaitStrategy.SleepWait();
    }
}
