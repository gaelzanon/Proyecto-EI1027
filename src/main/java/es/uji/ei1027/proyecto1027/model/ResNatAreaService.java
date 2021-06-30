package es.uji.ei1027.proyecto1027.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ResNatAreaService {
    private String code_area;
    private String code;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate startTime;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate endTime;

    public String getCode_area() {
        return code_area;
    }

    public void setCode_area(String code_area) {
        this.code_area = code_area;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "ResNatAreaService{" +
                "code_area='" + code_area + '\'' +
                ", code='" + code + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
