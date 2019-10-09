package tp.abs;

public enum SensibleLattice {
    BOTTOM("bottom"), LOW("low"), HIGH("high"), TOP("top");

    private String name;

    @Override
    public String toString() {
        return this.name;
    }

    SensibleLattice(String name) {
        this.name = name;
    }

    public static SensibleLattice supremeBetween(SensibleLattice val1, SensibleLattice val2) {
        if (val1.equals(TOP) || val2.equals(TOP)) {
            return TOP;
        }
        if (val1.equals(HIGH) || val2.equals(HIGH)) {
            return HIGH;
        }
        if (val1.equals(LOW) || val2.equals(LOW)) {
            return LOW;
        }
        return BOTTOM;
    }

    public static boolean isSensible(SensibleLattice val) {
        return val == HIGH || val == TOP;
    }
}
