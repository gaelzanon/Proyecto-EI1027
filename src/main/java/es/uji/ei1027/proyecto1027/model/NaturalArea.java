package es.uji.ei1027.proyecto1027.model;

import java.time.LocalDate;

public class NaturalArea {
    private String codeArea;
    private String name;
    private String address;
    private int currentCapacity;
    private int maxCapacity;
    private String descripcion;
    private TypeNaturalAreaEnum type;
    private LocalDate registrationDate;
    private StatesNaturalAreaEnum state;
    private LocalDate startTime;
    private LocalDate endTime;
    private String munCode;

    public NaturalArea() {
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

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = TypeNaturalAreaEnum.valueOf(type);
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getState() {
        return state.toString();
    }

    public void setState(String state) {
        this.state = StatesNaturalAreaEnum.valueOf(state);
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getMunCode() {
        return munCode;
    }

    public void setMunCode(String munCode) {
        this.munCode = munCode;
    }

    @Override
    public String toString() {
        return "NaturalArea{" +
                "codeArea='" + codeArea + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", currentCapacity=" + currentCapacity +
                ", maxCapacity=" + maxCapacity +
                ", descripcion='" + descripcion + '\'' +
                ", type='" + type + '\'' +
                ", registrationDate=" + registrationDate +
                ", state='" + state + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", munCode='" + munCode + '\'' +
                '}';
    }
}
