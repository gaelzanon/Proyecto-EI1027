package es.uji.ei1027.proyecto1027.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private String address;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private String qr;
    private String code;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate creationDate;
    private String nifCitizen;
    private String state;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    public String getCodeZone() {
        return codeZone;
    }

    public void setCodeZone(String codeZone) {
        this.codeZone = codeZone;
    }

    private String codeZone;
    private String codeArea;

    public Reservation() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getNifCitizen() {
        return nifCitizen;
    }

    public void setNifCitizen(String nifCitizen) {
        this.nifCitizen = nifCitizen;
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

    public String getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(String codeArea) {
        this.codeArea = codeArea;
    }

    @Override
    public String toString() {
        return "reservation{" +
                "address='" + address + '\'' +
                ", date=" + date +
                ", qr='" + qr + '\'' +
                ", code='" + code + '\'' +
                ", creationDate=" + creationDate +
                ", nifCitizen='" + nifCitizen + '\'' +
                ", state=" + state +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", codeZone='" + codeZone + '\'' +
                ", codeArea='" + codeArea + '\'' +
                '}';
    }
}
