package jvm;

public class Deadlock {

    static class Friend {
        //朋友名字  
        private final String name;

        //朋友实体类型的构造方法  
        public Friend(String name) {
            this.name = name;
        }

        //获取名字  
        public String getName() {
            return this.name;
        }

        //朋友向我鞠躬方法，（同步的）  
        public synchronized void bow(Friend bower) {
            System.out.format("%s: %s" + "  has bowed to me!%n", this.name, bower.getName());
            bower.bowBack(this);
        }

        //我回敬鞠躬方法，（同步的）  
        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s" + " has bowed back to me!%n", this.name, bower.getName());
        }
    }

    public static void main(String[] args) {
        //死锁模拟程序测试开始  
        //创建两个友人alphonse，Gaston  
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        //启动两位友人鞠躬的线程。  
        new Thread(new Runnable() {
            public void run() {
                alphonse.bow(gaston);
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                gaston.bow(alphonse);
            }
        }).start();
    }

}
