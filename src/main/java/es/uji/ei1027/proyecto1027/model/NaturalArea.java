package es.uji.ei1027.proyecto1027.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class NaturalArea {
    private String codeArea;
    private String name;
    private String address;
    private int currentCapacity;
    private int maxCapacity;
    private String descripcion;
    private String type_of_area;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate registrationDate;
    private String state;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private String munCode;

    @Override
    public String toString() {
        return "NaturalArea{" +
                "codeArea='" + codeArea + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", currentCapacity=" + currentCapacity +
                ", maxCapacity=" + maxCapacity +
                ", descripcion='" + descripcion + '\'' +
                ", type_of_area='" + type_of_area + '\'' +
                ", registrationDate=" + registrationDate +
                ", state='" + state + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", munCode='" + munCode + '\'' +
                '}';
    }

    public String getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(String codeArea) {
        this.codeArea = codeArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getType_of_area() {
        return type_of_area;
    }

    public void setType_of_area(String type_of_area) {
        this.type_of_area = type_of_area;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getMunCode() {
        return munCode;
    }

    public void setMunCode(String munCode) {
        this.munCode = munCode;
    }
}
