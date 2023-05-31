package PathFinder;



public class IntFloatList {
    private Integer[] intList;
    private Float floatValue;

    public IntFloatList(Integer[] intList, Float floatValue) {
        this.intList = intList;
        this.floatValue = floatValue;
    }

    public Integer[] getIntList() {
        return intList;
    }

    public Float getFloatValue() {
        return floatValue;
    }
}