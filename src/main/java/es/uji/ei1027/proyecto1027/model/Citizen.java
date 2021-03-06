package es.uji.ei1027.proyecto1027.model;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class Citizen {
    private String NIF;
    private String password;
    private String tipoUsuario;
    private String name;
    private String surname;
    private String email;
    private String address;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate date_of_birth;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate registration_date;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "NIF='" + NIF + '\'' +
                ", password='" + password + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", registration_date=" + registration_date +
                '}';
    }
}
