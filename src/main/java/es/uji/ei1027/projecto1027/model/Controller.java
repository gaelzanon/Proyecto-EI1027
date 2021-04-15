package es.uji.ei1027.projecto1027.model;

public class Controller {
    private String NIF;
    private String name;
    private String surname;
    private String email;
    private String code_area;

    public Controller(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode_area() {
        return code_area;
    }

    public void setCode_area(String code_area) {
        this.code_area = code_area;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }
    @Override
    public String toString() {
        return "Citizen{" +
                "nif='" + NIF + "\'" +
                "nom='" + name + "\'" +
                ", surname='" + surname + "\'" +
                ", email='" + email + "\'" +
                ", Code_area=" + code_area +
                "}";
    }
}
