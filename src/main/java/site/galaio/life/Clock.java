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
}
