package site.galaio.life;

import site.galaio.concurrent.ConditionVariable;
import site.galaio.util.AssertUtil;

import java.awt.*;

/**
 * Created by GalaIO on 2018/4/24.
 */
public class Neighborhood implements Cell {

    /**
     * Block if reading is not permitted because
     * the gird is transitioning to the next state.
     */
    private static final ConditionVariable readingPermitted = new ConditionVariable(true);

    /**
     * Returns true if none od the cells in the
     * neighborhood changed state suring the last transition.
     */
    private boolean amActive = false;

    /**
     * the actual entity of cells.
     */
    private final Cell[][] grid;

    /**
     * the square grid's size.
     */
    private final int gridSize;

    private boolean oneLastRefreshRequired = false;

    private Direction activeEdges = new Direction(Direction.NONE);

    public Neighborhood(int gridSize, Cell prototype) {
        this.gridSize = gridSize;
        this.grid = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                this.grid[i][j] = prototype.create();
            }
        }
    }

    /**
     * a example of flyweight pattern.
     * cell's life determined by surrounding cells.
     * the final unit is residentm neighborhood is a composite of universe and resident.
     * so the key is spread the action to lowest unit, with its surrounding unit.
     * @param north
     * @param south
     * @param east
     * @param west
     * @param northeast
     * @param northwest
     * @param southeast
     * @param southwest
     * @return
     */
    @Override
    public boolean figureNextState(Cell north, Cell south, Cell east, Cell west, Cell northeast, Cell northwest, Cell southeast, Cell southwest) {

        Cell subCellNorth, subCellSouth, subCellWest, subCellEast,
                subCellNorthWest, subCellNorthEast, subCellSouthWest, subCellSouthEast;
        boolean noOneChanged = true;
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                // figure the subcell surrounding cell.
                if (row == 0) {
                    subCellNorthWest = (column == 0) ?
                            northwest.edge(gridSize - 1, gridSize - 1) :
                            north.edge(gridSize - 1, column - 1);
                    subCellNorth = north.edge(gridSize - 1, column);
                    subCellNorthEast = (column == gridSize - 1) ?
                            northeast.edge(gridSize - 1, 0) :
                            north.edge(gridSize - 1, column + 1);
                } else {

                    subCellNorthWest = (column == 0) ?
                            northwest.edge(row-1, gridSize - 1) :
                            north.edge(row - 1, column - 1);
                    subCellNorth = grid[row-1][column];
                    subCellNorthEast = (column == gridSize - 1) ?
                            northeast.edge(row - 1, 0) :
                            north.edge(row - 1, column + 1);
                }
                subCellWest = (column == 0) ? west.edge(row, gridSize - 1) : grid[row][column - 1];
                subCellEast = (column == gridSize - 1) ? east.edge(row, 0) : grid[row][column + 1];
                if (row == gridSize - 1) {
                    subCellSouthWest = (column == 0) ?
                            southwest.edge(0, gridSize - 1) :
                            south.edge(0, column - 1);
                    subCellSouth = south.edge(0, column);
                    subCellSouthEast = (column == gridSize - 1) ? subCellSouthWest.edge(0, 0) : south.edge(0, column + 1);
                } else {
                    subCellSouthWest = (column == 0) ?
                            west.edge(row + 1, gridSize - 1) :
                            grid[row + 1][column - 1];
                    subCellSouth = grid[row + 1][column];
                    subCellSouthEast = (column == gridSize - 1) ? east.edge(row + 1, 0) : grid[row + 1][column + 1];
                }
                if (grid[row][column].figureNextState(subCellNorth, subCellSouth, subCellEast, subCellWest,
                        subCellNorthEast, subCellNorthWest, subCellSouthEast, subCellSouthWest)) {
                    noOneChanged = false;
                }
            }
        }
        if (amActive && noOneChanged) {
            oneLastRefreshRequired = true;
        }

        return (amActive = !noOneChanged);
    }

    /**
     * if request the edge, return the lower level entity.
     * @param row
     * @param colum
     * @return
     */
    @Override
    public Cell edge(int row, int colum) {
        AssertUtil.assertTrue((row == 0 | row == gridSize - 1) || (colum == 0 || colum == gridSize - 1),
                new UnsupportedOperationException("central cell requested from edge()"));
        return grid[row][colum];
    }

    @Override
    public boolean transition() {
        return false;
    }

    /**
     * if the cell is not active or state-changing or force-drawing, then return.
     * when the entity is active, then color it, and the most important is to transimit to subcells.
     * @param g
     * @param here describes th bounds of the current cell.
     * @param drawAll true, draw an entire conponent cell;
     */
    @Override
    public void redraw(Graphics g, Rectangle here, boolean drawAll) {
        if (!amActive && !oneLastRefreshRequired && !drawAll) {
            return;
        }

        int subCellWidth = here.width / gridSize;
        int subCellHeigh = here.height / gridSize;
        Rectangle subCellBounds = new Rectangle(here.x, here.y, subCellWidth, subCellHeigh);
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                grid[row][column].redraw(g, subCellBounds, drawAll);
                subCellBounds.x += subCellWidth;
            }
            subCellBounds.x = here.x;
            subCellBounds.y += subCellHeigh;
        }

        if (amActive) {
            g = g.create();
            g.setColor(Color.BLUE);
            g.drawRect(here.x + 1, here.y + 1, here.width - 2, here.height - 2);
            g.dispose();
        }

    }

    /**
     *
     * @param here
     * @param surface
     */
    @Override
    public void userClicked(Point here, Rectangle surface) {

    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public int widthInCells() {
        return gridSize * grid[0][0].widthInCells();
    }

    @Override
    public Cell create() {
        return new Neighborhood(gridSize, grid[0][0]);
    }

    @Override
    public Direction isDisruptiveTo() {
        return activeEdges;
    }

    @Override
    public void clear() {
        activeEdges.clear();
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                grid[row][column].clear();
            }
        }
        amActive = false;
    }
}
