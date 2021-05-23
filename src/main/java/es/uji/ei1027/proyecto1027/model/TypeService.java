package es.uji.ei1027.proyecto1027.model;

public class TypeService {
    private String type;

    @Override
    public String toString() {
        return "TypeService{" +
                "type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
