package es.uji.ei1027.proyecto1027.model;

public class MunicipalityManager {
    private String NIF;
    private String areaCode;
    private String municipalityName;
    private String email;

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
                ", areaCode='" + areaCode + '\'' +
                ", municipalityName='" + municipalityName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
