package es.uji.ei1027.proyecto1027.model;

public class ResNatAreaService {
    private String code_area;
    private String code;

    public String getCode_area() {
        return code_area;
    }

    public void setCode_area(String code_area) {
        this.code_area = code_area;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResNatAreaService{" +
                "Code_area='" + code_area + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
