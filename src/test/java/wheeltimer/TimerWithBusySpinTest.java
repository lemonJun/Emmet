package wheeltimer;

import com.wheeltimer.WaitStrategy;

public class TimerWithBusySpinTest extends AbstractTimerTest {

    @Override
    public WaitStrategy waitStrategy() {
        return new WaitStrategy.BusySpinWait();
    }

}
