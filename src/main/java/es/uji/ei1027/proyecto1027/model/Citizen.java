package es.uji.ei1027.proyecto1027.model;
import java.time.LocalDate;


public class Citizen {
    private String NIF;
    private String name;
    private String surname;
    private String email;
    private String address;
    private LocalDate date_of_birth;
    private LocalDate registration_date;

    public Citizen() {
    }

    public String getNIF() { return NIF; }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getdate_of_birth() {
        return date_of_birth;
    }

    public void setdate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public LocalDate getregistration_date() {
        return registration_date;
    }

    public void setregistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }

    @Override
    public String toString() {
        return "citizen{" +
                "nif='" + NIF + "\'" +
                "nom='" + name + "\'" +
                ", surname='" + surname + "\'" +
                ", email='" + email + "\'" +
                ", Address=" + address +
                ", fechaNacimiento='" + date_of_birth + "\'" +
                ", fechaRegistro='" + registration_date + "\'" +
                "}";
    }
}
