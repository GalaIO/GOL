package site.galaio.concurrent;

/**
 * Created by GalaIO on 2018/4/24.
 * This calss is a simplified version of the com.asynch.condition class,
 * use it to wait for some condition to become true.
 * <PRE>
 * ConditionVariable hellFreezeOver = new ConditionVariable(false);
 * <p>
 * Thread 1:
 * hellFreezeOver.waitForTrue();
 * Thread 2:
 * hellFreezeOver.set(true);
 * </PRE>
 */
public class ConditionVariable {

    private volatile boolean isTrue;

    public ConditionVariable(boolean isTrue) {
        this.isTrue = isTrue;
    }

    public synchronized boolean isTrue() {
        return isTrue;
    }

    public synchronized void set(boolean how) {
        if ((isTrue = how) == true) {
            notifyAll();
        }
    }

    public final synchronized void waitForTrue() throws InterruptedException {
        while (!isTrue) {
            wait();
        }
    }

}
