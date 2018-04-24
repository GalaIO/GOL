package site.galaio.life;

import site.galaio.concurrent.ConditionVariable;

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

    @Override
    public boolean figureNextState(Cell north, Cell south, Cell east, Cell west, Cell northeast, Cell northwest, Cell southeast, Cell southwest) {
        return false;
    }

    @Override
    public Cell edge(int row, int colum) {
        return null;
    }

    @Override
    public boolean transition() {
        return false;
    }

    @Override
    public void redraw(Graphics g, Rectangle here, boolean drawAll) {

    }

    @Override
    public void userClicked(Point here, Rectangle surface) {

    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public int widthInCells() {
        return 0;
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

    }
}
