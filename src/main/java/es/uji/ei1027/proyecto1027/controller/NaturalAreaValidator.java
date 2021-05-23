package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.NaturalArea;
import es.uji.ei1027.proyecto1027.model.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalTime;

public class NaturalAreaValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return NaturalArea.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        NaturalArea naturalArea = (NaturalArea) obj;
        if (naturalArea.getCodeArea().trim().equals(""))
            errors.rejectValue("codeArea", "obligatori",
                    "Se debe introducir un valor");
        if (naturalArea.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Se debe introducir un nombre");
        if (naturalArea.getAddress().trim().equals(""))
            errors.rejectValue("address", "obligatori",
                    "Se debe introducir una direccion");
        if (naturalArea.getMaxCapacity()<=0)
            errors.rejectValue("maxCapacity", "obligatori",
                    "La capacidad maxima debe de ser mayor que 0");
        if (naturalArea.getDescripcion().trim().equals(""))
            errors.rejectValue("descripcion", "obligatori",
                    "Se debe introducir una descripcion");
        if (naturalArea.getType_of_area().trim().equals(""))
            errors.rejectValue("type_of_area", "obligatori",
                    "Se debe introducir un tipo de area");
        if (naturalArea.getStartTime()==null || naturalArea.getEndTime()==null) {
            errors.rejectValue("startTime", "obligatori",
                    "Se debe introducir una fecha de apertura");
            errors.rejectValue("endTime", "obligatori",
                    "Se debe introducir una fecha de cierre");
        } else if (naturalArea.getStartTime().compareTo(naturalArea.getEndTime())>=0){
            errors.rejectValue("startTime", "obligatori",
                    "La fecha de inicio no puede ser mas grande que la de fin");
            errors.rejectValue("endTime", "obligatori",
                    "La fecha de inicio no puede ser mas grande que la de fin");
        }
        if (naturalArea.getMunCode().trim().equals(""))
            errors.rejectValue("munCode", "obligatori",
                    "Se debe introducir un codigo de municipio");
    }
}
