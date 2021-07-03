package es.uji.ei1027.proyecto1027.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Solicitud {
    private String code;
    private String name;
    private String descripcion;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate Fecha;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDate fecha) {
        Fecha = fecha;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", Fecha=" + Fecha +
                '}';
    }

}