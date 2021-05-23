package es.uji.ei1027.proyecto1027.model;

public class TypeNaturalArea {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //Lo hacemos asi para que en la vista se vea bien
    @Override
    public String toString() {
        return type;
    }
}
