package es.uji.ei1027.proyecto1027.model;

public class R_NArea_service {
    private String areaCode;
    private String code;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "R_NArea_service{" +
                "areaCode='" + areaCode + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
