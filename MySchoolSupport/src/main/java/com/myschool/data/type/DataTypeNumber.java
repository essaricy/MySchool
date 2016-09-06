package com.myschool.data.type;


public class DataTypeNumber extends DataType {

    private boolean random;

    private int start;

    private int increment;

    private int randomMin;

    private int randomMax;

    public DataTypeNumber(String name) {
        this(name, false, 0, 0, 0, 0);
    }

    public DataTypeNumber(String name, boolean random) {
        this(name, true, 0, 0, 0, 0);
    }

    public DataTypeNumber(String name, boolean random, int randomMin, int randomMax) {
        this(name, true, 0, 0, 0, 0);
    }

    public DataTypeNumber(String name, int start, int increment) {
        this(name, false, start, increment, 0, 0);
    }

    public DataTypeNumber(String name, boolean random, int start, int increment, int randomMin, int randomMax) {
        super(name);
        this.random=random;
        this.start=start;
        this.increment=increment;
        this.randomMin=randomMin;
        this.randomMax=randomMax;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    
    public int getRandomMin() {
        return randomMin;
    }

    public void setRandomMin(int randomMin) {
        this.randomMin = randomMin;
    }

    public int getRandomMax() {
        return randomMax;
    }

    public void setRandomMax(int randomMax) {
        this.randomMax = randomMax;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("DataTypeNumber ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("random = ").append(this.random).append(SEPARATOR)
            .append("start = ").append(this.start).append(SEPARATOR)
            .append("increment = ").append(this.increment).append(SEPARATOR)
            .append("randomMin = ").append(this.randomMin).append(SEPARATOR)
            .append("randomMax = ").append(this.randomMax).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
