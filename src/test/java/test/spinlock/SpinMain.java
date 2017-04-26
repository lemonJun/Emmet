package test.spinlock;

public class SpinMain {
    private static TimeCost timeCost = new TimeCost(new TASLock());

    //    private static TimeCost timeCost = new TimeCost(new TTASLock());

    public static void method() {
        timeCost.lock();
        //int a = 10;  
        timeCost.unlock();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    method();
                }
            });
            t.start();
        }
    }
}
