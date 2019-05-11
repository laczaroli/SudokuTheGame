package state;

public enum Difficulty {
    TEST(1),
    EASY(30),
    MEDIUM(50  ),
    HARD(75);

    Difficulty(int numberToRemove) {
        this.numberToRemove = numberToRemove;
    }

    int numberToRemove;
    public int getValue() {
        return numberToRemove;
    }


}
