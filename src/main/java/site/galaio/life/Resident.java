package site.galaio.life;

import site.galaio.util.AssertUtil;
import site.galaio.util.exception.UserAssertException;

import java.awt.*;

/**
 * Created by GalaIO on 2018/4/23.
 * Resident is a real entity of cell,
 * It's a lowest level control unit.
 */
public class Resident implements Cell{

    private static final Color BORDER_COLOR = Color.YELLOW;
    private static final Color LIVE_COLOR = Color.RED;
    private static final Color DEAD_COLOR = Color.WHITE;

    private boolean amAlive = false;
    private boolean willBeAlive = false;

    @Override
    public boolean figureNextState(Cell north, Cell south, Cell east, Cell west,
                                        Cell northeast, Cell northwest, Cell southeast, Cell southwest) {
        try {
            verify(north, "north");
            verify(south, "south");
            verify(east, "east");
            verify(west, "west");
            verify(northeast, "northeast");
            verify(northwest, "northwest");
            verify(southeast, "southeast");
            verify(southwest, "southwest");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        int lived_neighbors = 0;
        if (north.isAlive()) {
            lived_neighbors++;
        }
        if (south.isAlive()) {
            lived_neighbors++;
        }
        if (west.isAlive()) {
            lived_neighbors++;
        }
        if (east.isAlive()) {
            lived_neighbors++;
        }
        if (southeast.isAlive()) {
            lived_neighbors++;
        }
        if (southwest.isAlive()) {
            lived_neighbors++;
        }
        if (northeast.isAlive()) {
            lived_neighbors++;
        }
        if (northwest.isAlive()) {
            lived_neighbors++;
        }
        willBeAlive = (lived_neighbors == 3 || (amAlive && lived_neighbors == 2));
        return !isStable();
    }

    private boolean isStable() {
        return amAlive == willBeAlive;
    }

    private void verify(Cell cell, String direction) throws UserAssertException {
        AssertUtil.assertTrue(cell != null && (cell instanceof Resident || cell == Cell.DUMMY),
                "incorrect type of " + direction + ": " + cell.getClass().getName());
    }

    @Override
    public Cell edge(int row, int colum) {
        if (row == 0 && colum == 0) {
            return this;
        }
        return null;
    }

    @Override
    public boolean transition() {
        boolean changed = isStable();
        amAlive = willBeAlive;
        return changed;
    }

    @Override
    public void redraw(Graphics g, Rectangle here, boolean drawAll) {
        g = g.create();
        g.setColor(amAlive ? LIVE_COLOR : DEAD_COLOR);
        g.fillRect(here.x + 1, here.y + 1, here.width - 1, here.height - 1);

        g.setColor(BORDER_COLOR);
        g.drawLine(here.x, here.y, here.x, here.y + here.height);
        g.drawLine(here.x, here.y, here.x + here.width, here.y);

        g.dispose();
    }

    @Override
    public void userClicked(Point here, Rectangle surface) {
        amAlive = !amAlive;
    }

    @Override
    public boolean isAlive() {
        return amAlive;
    }

    @Override
    public int widthInCells() {
        return 1;
    }

    @Override
    public Cell create() {
        return new Resident();
    }

    @Override
    public Direction isDisruptiveTo() {
        return isStable() ? Direction.NONE : Direction.ALL;
    }

    @Override
    public void clear() {
        amAlive = willBeAlive = false;
    }
}
