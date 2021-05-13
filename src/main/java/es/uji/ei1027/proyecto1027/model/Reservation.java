package es.uji.ei1027.proyecto1027.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Reservation {
    private String address;
    private LocalDate date;
    private String qr;
    private String code;
    private LocalDate creationDate;
    private String nifCitizen;
    private String state;
    private LocalDate startTime;
    @DateTimeFormat(pattern = "HH:mm:ss.SSS")
    private LocalDate endTime;
    private int cols;
    private int row;
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

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(String codeArea) {
        this.codeArea = codeArea;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "address='" + address + '\'' +
                ", date=" + date +
                ", qr='" + qr + '\'' +
                ", code='" + code + '\'' +
                ", creationDate=" + creationDate +
                ", nifCitizen='" + nifCitizen + '\'' +
                ", state=" + state +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", cols=" + cols +
                ", row=" + row +
                ", codeArea='" + codeArea + '\'' +
                '}';
    }
}
