package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.Controller;
import es.uji.ei1027.proyecto1027.model.Zone;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ZoneValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Zone.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Zone zone = (Zone) obj;
        if (zone.getAreaCode().trim().equals(""))
            errors.rejectValue("areaCode", "obligatori",
                    "Se debe introducir un valor");
        if (zone.getCapacity()<=0)
            errors.rejectValue("capacity", "obligatori",
                    "La capacidad maxima debe de ser mayor que 0");
        if (zone.getCol()<0)
            errors.rejectValue("col", "obligatori",
                    "La columna no puede ser menor que 0");
        if (zone.getRow()<0)
            errors.rejectValue("row", "obligatori",
                    "La columna no puede ser menor que 0");
    }
}
