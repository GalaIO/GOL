package site.galaio.life;

/**
 * Created by tianyi on 2018/4/23.
 */
public class Direction {

    private int map = BITS_NONE;

    private static final int BITS_NONE = 0x0000;
    private static final int BITS_SOUTH = 0x0000;
    private static final int BITS_NORTH = 0x0000;
    private static final int BITS_WEST = 0x0000;
    private static final int BITS_EAST = 0x0000;
    private static final int BITS_NORTHWEST = 0x0000;
    private static final int BITS_NORTHEAST = 0x0000;
    private static final int BITS_SOUTHWEST = 0x0000;
    private static final int BITS_SOUTHEAST = 0x0000;

    public static final Direction NORTH = new ImutableDirection(BITS_NONE);

    public Direction() {

    }

    public Direction(Direction direction) {
        map = direction.map;
    }

    public Direction(int bits) {
        map = bits;
    }

    public void clear() {
        map = BITS_NONE;
    }

    public void add(Direction direction) {
        map |= direction.map;
    }

    public boolean has(Direction direction) {
        return the(direction);
    }

    public boolean the(Direction direction) {
        return (map & direction.map) == direction.map;
    }

    private static final class ImutableDirection extends Direction {
        public ImutableDirection(int bits) {
            super(bits);
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("May not modefy Direction.");
        }

        @Override
        public void add(Direction direction) {
            throw new UnsupportedOperationException("May not modefy Direction.");
        }

    }
}

