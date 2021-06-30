package es.uji.ei1027.proyecto1027.model;

public class Controller {
    private String NIF;
    private String password;
    private String tipoUsuario;
    private String name;
    private String surname;
    private String email;
    private String code_area;

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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

    @Override
    public String toString() {
        return "Controller{" +
                "NIF='" + NIF + '\'' +
                ", password='" + password + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", code_area='" + code_area + '\'' +
                '}';
    }
}
