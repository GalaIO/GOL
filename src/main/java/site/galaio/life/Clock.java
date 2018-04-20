package site.galaio.life;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by GalaIO on 2018/4/20.
 * clock class
 * a singel instance, provide tick...
 */
public class Clock {

    private Timer clock = new Timer();
    private TimerTask tick;
    private volatile static Clock instance;
    private Publisher publisher = new Publisher();

    /**
     * a private constructor.
     * for init some menus, we should instance Clock later.
     */
    private Clock() {
        createMenus();
    }

    private void createMenus() {


    }

    /**
     * start up the clock.
     * @param millisecondsBetweenTicks the number of milliseconds between ticks. the input of 0 should be stopped.
     */
    public void startTicking(int millisecondsBetweenTicks) {
        if (tick != null) {
            tick.cancel();
            tick = null;
        }

        if (millisecondsBetweenTicks > 0) {
            tick = new TimerTask() {
                @Override
                public void run() {
                    tick();
                }
            };
            clock.scheduleAtFixedRate(tick, 0, millisecondsBetweenTicks);
        }
    }

    public void stop() {
        startTicking(0);
    }

    /**
     * build a observer sequence.
     * @param observer
     */
    public void addClockListener(Listener observer) {
        publisher.subscribe(observer);
    }

    /**
     *
     */
    public void tick() {
        publisher.publish(new Publisher.Distributor() {
            public void deliverto(Object subscriber) {
                ((Listener)subscriber).tick();
            }
        });
    }

    /**
     * DCL to get instance of Clock.
     * @return
     */
    public static Clock instance() {
        if (instance == null) {
            synchronized (Clock.class) {
                if (instance == null) {
                    instance = new Clock();
                }
            }
        }
        return instance;
    }

    /**
     * subject order.
     */
    public interface Listener {
        void tick();
    }

}
