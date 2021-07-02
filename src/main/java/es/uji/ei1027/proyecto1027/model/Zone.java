package es.uji.ei1027.proyecto1027.model;

public class Zone {
    private String code;
    private int col;
    private int row;
    private int capacity;
    private String areaCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "code=" + code +
                ", col=" + col +
                ", row=" + row +
                ", capacity=" + capacity +
                ", areaCode='" + areaCode + '\'' +
                '}';
    }
}
