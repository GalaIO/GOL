package site.galaio.ui;

import site.galaio.util.AssertUtil;
import site.galaio.util.exception.UserAssertException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by tianyi on 2018/4/22.
 */
public final class MenuSite {

    private static JFrame menuFrame = null;
    private static JMenuBar menuBar = null;

    // save the object-menuitem map.
    private static Map requesters = new HashMap();

    private static Properties nameMap;

    private static Pattern shortcutExtractor = Pattern.compile("\\s*([^;]+?\\s*)" //value
        + "(;\\s*([^\\s].*?))\\s*$"); //shortcut

    private static final LinkedList emnuBarContents = new LinkedList();

    private MenuSite() {

    }

    private static boolean valid() throws UserAssertException {
        AssertUtil.assertTrue(menuFrame != null, "Menusite not establish!");
        AssertUtil.assertTrue(menuBar != null, "Menusite not establish!");
        return true;
    }

    public synchronized static void establish(JFrame container) throws UserAssertException {
        AssertUtil.assertTrue(container != null);
        AssertUtil.assertTrue(menuFrame == null, "try to establish more than one MenuSite");

        menuFrame = container;
        menuBar = new JMenuBar();
        menuFrame.setJMenuBar(menuBar);

        AssertUtil.assertTrue(valid());
    }

    public static void addMenu(String menuName) throws UserAssertException {
        findOrCreateSubmenuByName(menuName);
    }

    public static void addLine(String menuName, String itemName, ActionListener listener) throws Exception {
        AssertUtil.assertTrue(menuName != null && itemName != null);

        // the certain item of menu.
        Component element;
        if (itemName.equals("-")) {
            element = new JSeparator();
        } else {
            AssertUtil.assertTrue(listener != null);
            JMenuItem menuItem = new JMenuItem(itemName);
            menuItem.setName(itemName);
            menuItem.addActionListener(listener);
            element = menuItem;
        }
        JMenu menu = findOrCreateSubmenuByName(menuName);
        AssertUtil.assertTrue(menu != null, new IllegalArgumentException("add line cannot find the certain menu!"));
        menu.add(element);
    }

    public static boolean removeMenus(String menuName) throws UserAssertException {
        AssertUtil.assertTrue(menuName != null);

        MenuElement[] subMenus = menuBar.getSubElements();
        for (int i = 0; i < subMenus.length; i++) {
            if (subMenus[i].getComponent().getName().equals(menuName)) {
                menuBar.remove(subMenus[i].getComponent());
                return true;
            }
        }
        return false;
    }

    public static boolean setEnable(String menuName, boolean enable) throws UserAssertException {
        AssertUtil.assertTrue(menuName != null);

        MenuElement[] subMenus = menuBar.getSubElements();
        for (int i = 0; i < subMenus.length; i++) {
            if (subMenus[i].getComponent().getName().equals(menuName)) {
                subMenus[i].getComponent().setEnabled(enable);
                return true;
            }
        }
        return false;
    }

    private static JMenu findOrCreateSubmenuByName(String menuName) throws UserAssertException {
        AssertUtil.assertTrue(menuName != null);
        MenuElement[] subMenus = menuBar.getSubElements();
        for (int i = 0; i < subMenus.length; i++) {
            if (subMenus[i].getComponent().getName().equals(menuName)) {
                return (JMenu)subMenus[i].getComponent();
            }
        }
        // create a new
        JMenu menu = new JMenu(menuName);
        menu.setName(menuName);
        menuBar.add(menu);
        return menu;
    }

}
