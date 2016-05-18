package model.entities;

import java.util.Optional;

/**
 * enum that tracks every stat type.
 *
 */
public enum StatType {
    HP("HitPoints",1, 100, 20000),
    LEVEL("Level",1, 1, 100),
    SPEED("Speed",1, 10, 120),
    MANA("Mana",0, 50, 5000),
    MANAREGEN("Mana Regen",0, 5, 500),
    EXP("Experience", 0, 0, Integer.MAX_VALUE),
    GOLD("Gold", 0, 0, Integer.MAX_VALUE);

    private final String name;
    private final int minValue;
    private final int stdValue;
    private final int maxValue;

    /**
     * @param name
     * @param minValue
     * @param stdValue
     * @param maxValue
     */
    StatType(final String name, final int minValue, final int stdValue, final int maxValue) {
        this.name = name;
        this.minValue = minValue;
        this.stdValue = stdValue;
        this.maxValue = maxValue;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the minValue
     */
    public int getMinValue() {
        return this.minValue;
    }

    /**
     * @return the stdValue
     */
    public int getStdValue() {
        return this.stdValue;
    }

    /**
     * @return the maxValue
     */
    public int getMaxValue() {
        return this.maxValue;
    }

    /**
     * checks if a value is inside the allowed range, if not throws an exception.
     * if value is not present returns the standard one. 
     * @param value stat value to be checked
     * @return correct value
     */
    public int check(final Optional<Integer> value) {
        if (value.isPresent()) {
            if (value.get() < this.minValue || value.get() > this.maxValue) {
                throw new IllegalArgumentException("Wrong value for " + this.getName() + " found! -> " + value 
                        + " \nMinValue: " + this.minValue + "\nMaxValue: " + this.maxValue);
            }
            return value.get();
        }
        return this.stdValue;
    }

    @Override
    public String toString() {
        return this.name();
    }

}