package site.galaio.life;

import site.galaio.ui.MenuSite;
import site.galaio.util.exception.UserAssertException;

import javax.swing.*;
import java.awt.*;

/**
 * Created by tianyi on 2018/4/22.
 * contain the main method, the main container/frame.
 */
public class Life extends JFrame {

    // the main chessboard
    private static JComponent universe;

    public static void main(String[] args) throws Exception {
        new Life();
    }

    private Life() throws Exception {
        super("The game of life.");
        //must establish the MenuSite early, a subcomponeent puts menu on it.
        MenuSite.establish(this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
//        getContentPane().add(Universe.instance(), BorderLayout.CENTER);

        pack();
        setVisible(true);

    }

}
