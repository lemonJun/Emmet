package wheeltimer;

import com.wheeltimer.WaitStrategy;

public class TimerWithYieldWait extends AbstractTimerTest {
    @Override
    public WaitStrategy waitStrategy() {
        return new WaitStrategy.YieldingWait();
    }
}
