package site.galaio.life;

import site.galaio.util.Direction;

import java.awt.*;

/**
 * Created by tianyi on 2018/4/22.
 */
public interface Cell {

    /**
     * figure out the next state of the cell, given the specified neighbors.
     * @param north
     * @param south
     * @param east
     * @param west
     * @param northeast
     * @param northwest
     * @param southeast
     * @param southwest
     * @return true when the cell is unstable.
     */
    boolean figureNextState(Cell north, Cell south, Cell east, Cell west, Cell northeast, Cell northwest, Cell southeast, Cell southwest);

    /**
     * Access a specific contained cell located at the edge of the composite cell.
     * @param row
     * @param colum
     * @return
     */
    Cell edge(int row, int colum);

    /**
     * Transition to the state computed by the most recent call to
     * @return true if a changed of the state happened suring the transition
     */
    boolean transition();

    /**
     * redraw yourself in the indicated,this method is meant for a conditional redraw,
     * where some if the cells might mot be refreshed.
     * @param g
     * @param here describes th bounds of the current cell.
     * @param drawAll true, draw an entire conponent cell;
     */
    void redraw(Graphics g, Rectangle here, boolean drawAll);

    /**
     * A user had clicked somewhere within you
     * @param here
     * @param surface
     */
    void userClicked(Point here, Rectangle surface);

    /**
     * Return true if this cell or any subcells are alive.
     * @return
     */
    boolean isAlive();

    /**
     * Return the specified width plus the current cell's width
     * @return
     */
    int widthInCells();

    /**
     * Return a fresh object identical to yourself in content.
     * @return
     */
    Cell create();

    /**
     * Return a Direction indicate the directions of the cells that have changed state
     * @return
     */
    Direction isDisruptiveTo();

    /**
     * set the cell and all subcells into a "dead" state.
     */
    void clear();

    /**
     * a DUMMY singleton represents a permanebtly dead cell. It's used for
     * the edges of the gird.
     */
    public static final Cell DUMMY = new Cell() {
        @Override
        public boolean figureNextState(Cell north, Cell south, Cell east, Cell west, Cell northeast, Cell northwest, Cell southeast, Cell southwest) {
            return true;
        }

        @Override
        public Cell edge(int row, int colum) {
            return this;
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
            return this;
        }

        @Override
        public Direction isDisruptiveTo() {
            return Direction.NONE;
        }

        @Override
        public void clear() {

        }
    };
}
