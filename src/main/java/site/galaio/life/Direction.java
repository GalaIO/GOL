package site.galaio.life;

/**
 * Created by tianyi on 2018/4/23.
 */
public class Direction {

    private int map = BITS_NONE;

    private static final int BITS_NONE = 0x0000;
    private static final int BITS_SOUTH = 0x0001;
    private static final int BITS_NORTH = 0x0002;
    private static final int BITS_WEST = 0x0004;
    private static final int BITS_EAST = 0x0008;
    private static final int BITS_NORTHWEST = 0x0010;
    private static final int BITS_NORTHEAST = 0x0020;
    private static final int BITS_SOUTHWEST = 0x0040;
    private static final int BITS_SOUTHEAST = 0x0080;
    private static final int BITS_ALL = 0x00FF;

    public static final Direction NONE = new ImutableDirection(BITS_NONE);
    public static final Direction SOUTH = new ImutableDirection(BITS_SOUTH);
    public static final Direction NORTH = new ImutableDirection(BITS_NORTH);
    public static final Direction NORTHEAST = new ImutableDirection(BITS_NORTHEAST);
    public static final Direction NORTHWEST = new ImutableDirection(BITS_NORTHWEST);
    public static final Direction SOUTHEAST = new ImutableDirection(BITS_SOUTHEAST);
    public static final Direction SOUTHWEST = new ImutableDirection(BITS_SOUTHWEST);
    public static final Direction WEST = new ImutableDirection(BITS_WEST);
    public static final Direction EAST = new ImutableDirection(BITS_EAST);
    public static final Direction ALL = new ImutableDirection(BITS_ALL);

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

