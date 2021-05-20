package es.uji.ei1027.proyecto1027.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class Service {
    private String code ;
    private String type ;
    private String description;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate initial_Date ;


    public Service() {}
    
    public String getCode() {
        return code ;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type ;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description ;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getInitial_Date() {
        return initial_Date ;
    }

    public void setInitial_Date(LocalDate initial_Date) {
        this.initial_Date = initial_Date;
    }


    @Override
    public String toString() {
        return "Service{" +"code='" + code + " \' " +
		", type='" + type + " \' " +
		", description='" + description + " \' " +
		", initial_Date=" + initial_Date +
		"}" ;
	}
}