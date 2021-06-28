package es.uji.ei1027.proyecto1027.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class Service {
    private String code ;
    private String type_of_service ;
    private String description;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate FechaReg;


    @Override
    public String toString() {
        return "Service{" +
                "code='" + code + '\'' +
                ", type_of_service='" + type_of_service + '\'' +
                ", description='" + description + '\'' +
                ", FechaReg=" + FechaReg +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType_of_service() {
        return type_of_service;
    }

    public void setType_of_service(String type_of_service) {
        this.type_of_service = type_of_service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFechaReg() {
        return FechaReg;
    }

    public void setFechaReg(LocalDate fechaReg) {
        FechaReg = fechaReg;
    }

}