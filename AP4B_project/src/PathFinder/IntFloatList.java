package PathFinder;



public class IntFloatList {
    private Integer[] intList;
    private Float floatValue;

    /**
     * An object that contains an array of integers and a float value
     * @param intList       The array of integers
     * @param floatValue    The float value
     */
    public IntFloatList(Integer[] intList, Float floatValue) {
        this.intList = intList;
        this.floatValue = floatValue;
    }

    /**
     * Returns the array of integers
     * @return  The array of integers
     */
    public Integer[] getIntList() {
        return intList;
    }

    /**
     * Returns the float value
     * @return  The float value
     */
    public Float getFloatValue() {
        return floatValue;
    }
}