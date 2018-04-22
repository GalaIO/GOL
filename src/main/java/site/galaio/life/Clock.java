package site.galaio.life;

import site.galaio.ui.MenuSite;
import site.galaio.util.Publisher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Clock() throws Exception {
        createMenus();
    }

    /**
     * first set up a single listener that will handle all the
     * menu-selection events except "Exit"
     */
    private void createMenus() throws Exception {
        ActionListener modifier = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = ((JMenuItem) e.getSource()).getName();
                char toDo = name.charAt(0);

                if (toDo == 'T') {
                    // single tock
                    tick();
                    return;
                }

                int tickMillseconds = 0;
                switch (toDo) {
                    case 'A':
                        tickMillseconds = 500;
                        break;
                    case 'S':
                        tickMillseconds = 150;
                        break;
                    case 'M':
                        tickMillseconds = 70;
                        break;
                    case 'F':
                        tickMillseconds = 30;
                        break;
                }
                startTicking(tickMillseconds);

            }
        };
        MenuSite.addLine("Go", "Halt", modifier);
        MenuSite.addLine("Go", "Tick(Single Step)", modifier);
        MenuSite.addLine("Go", "Agonizing", modifier);
        MenuSite.addLine("Go", "Slow", modifier);
        MenuSite.addLine("Go", "Medium", modifier);
        MenuSite.addLine("Go", "Fast", modifier);

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
        // command pattern
        publisher.publish(new Publisher.Distributor() {
            public void deliverTo(Object subscriber) {
                ((Listener)subscriber).tick();
            }
        });
    }

    /**
     * DCL to get instance of Clock.
     * @return
     */
    public static Clock instance() throws Exception {
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
