package site.galaio.life;

import site.galaio.ui.MenuSite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.nio.file.Files;

/**
 * Created by tianyi on 2018/4/22.
 * The Universe is a mediator that fits between the Swing
 * event model and the Life classes. It is also a Singleton
 * accessed via Universe.instance(). It handles all Swing events and
 * translates them into requests to the outermost Neighborhood. It
 * also creates the Composite Neighborhood.
 */
public class Universe extends JPanel {

    private final Cell outerMostCell;
    private final static Universe instance = new Universe();

    private static final int DEFAULT_GRID_SIZE = 8;
    private static final int DEFAULT_CELL_SIZE = 8;

    private Universe() {
        outerMostCell = new Neighborhood(DEFAULT_GRID_SIZE, new Neighborhood(DEFAULT_CELL_SIZE, new Resident()));
        final Dimension PREFERRED_SIZE = new Dimension(outerMostCell.widthInCells() * DEFAULT_CELL_SIZE, outerMostCell.widthInCells() * DEFAULT_CELL_SIZE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Rectangle bounds = getBounds();
                bounds.height /= outerMostCell.widthInCells();
                bounds.height *= outerMostCell.widthInCells();
                bounds.width = bounds.height;
                setBounds(bounds);
            }
        });

        setBackground(Color.white);
        setPreferredSize(PREFERRED_SIZE);
        setMaximumSize(PREFERRED_SIZE);
        setMinimumSize(PREFERRED_SIZE);
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Rectangle bounds = getBounds();
                bounds.x = 0;
                bounds.y = 0;
                outerMostCell.userClicked(e.getPoint(), bounds);
                repaint();
            }
        });
        try {
            MenuSite.addLine("Gird", "Clear", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    outerMostCell.clear();
                    repaint();
                }
            });

            MenuSite.addLine("Gird", "Load", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doLoad();
                }
            });

            MenuSite.addLine("Gird", "Store", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doStore();
                }
            });

            MenuSite.addLine("Gird", "Exit", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doExit();
                }
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "init failed!", "the game of life", JOptionPane.ERROR_MESSAGE);
        }

        Clock.instance().addClockListener(new Clock.Listener() {
            @Override
            public void tick() {
                if (outerMostCell.figureNextState(Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY,
                        Cell.DUMMY, Cell.DUMMY)) {
                    if (outerMostCell.transition()) {
                        refreshNow();
                    }
                }
            }
        });

    }

    public static Universe instance() {
        return instance;
    }

    private void doLoad() {
        FileInputStream in = null;
        try {
            in = new FileInputStream(Files.userSelected(".", ".life", "Life File", "Load"));
            Clock.instance().stop();
            outerMostCell.clear();
            in.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void doStore() {

    }

    public void paint(Graphics g) {
        Rectangle panelBounds = getBounds();
        Rectangle clipBounds = g.getClipBounds();
    }

}
