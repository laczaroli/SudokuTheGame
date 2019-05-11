package state;

public enum Square {
    EMPTY,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    /**
     * Returns the instance represented by the value specified.
     *
     * @param value the value representing an instance
     * @return the instance represented by the value specified
     * @throws IllegalArgumentException if the value specified does not
     * represent an instance
     */

    public static Square of(int value) {
        if (value < 0 || value >= values().length) {
            throw new IllegalArgumentException();
        }
        return values()[value];
    }

    /**
     * Returns the integer value that represents this instance.
     *
     * @return the integer value that represents this instance
     */
    public int getValue() {
        return ordinal();
    }

    /**
     * Rolls the cube to the direction specified.
     *
     * @param square the number should be written
     * @return the cube rolled to the direction specified
     * @throws UnsupportedOperationException if the method is invoked on the
     * {@link #EMPTY} instance.
     */
    public Square writeTo(Square square) {
        if (this == EMPTY) {
            throw new UnsupportedOperationException();
        }
        return values()[square.ordinal()];
    }

    public String toString() {
        return Integer.toString(ordinal());
    }
}
