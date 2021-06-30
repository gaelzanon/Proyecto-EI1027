package es.uji.ei1027.proyecto1027.model;

public class MunicipalityManager {
    private String NIF;
    private String password;
    private String tipoUsuario;
    private String code;
    private String name;
    private String municipalityName;
    private String email;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "MunicipalityManager{" +
                "NIF='" + NIF + '\'' +
                ", password='" + password + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", municipalityName='" + municipalityName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
