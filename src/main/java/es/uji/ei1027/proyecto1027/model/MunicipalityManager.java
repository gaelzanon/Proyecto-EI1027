package es.uji.ei1027.proyecto1027.model;

public class MunicipalityManager {
    private String NIF;
    private String code;
    private String name;
    private String municipalityName;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
                ", code='" + code + '\'' +
                ", Name='" + name + '\'' +
                ", municipalityName='" + municipalityName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
