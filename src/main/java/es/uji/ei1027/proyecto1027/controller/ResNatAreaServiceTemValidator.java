package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.model.ResNatAreaServiceTem;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ResNatAreaServiceTemValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return ResNatAreaServiceTem.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ResNatAreaServiceTem rnas = (ResNatAreaServiceTem) obj;
        if(rnas.getCode().trim().equals("NoDisp"))
            errors.rejectValue("code", "obligatori",
                    "No existen más servicios disponibles");
        if (rnas.getCode_area().trim().equals(""))
            errors.rejectValue("code_area", "obligatori",
                    "Se debe introducir un valor");
        if (rnas.getCode().trim().equals(""))
            errors.rejectValue("code", "obligatori",
                    "Se debe introducir un valor");
        if (rnas.getStartTime()==null || rnas.getEndTime()==null) {
            errors.rejectValue("startTime", "obligatori",
                    "Se debe introducir una fecha de apertura");
            errors.rejectValue("endTime", "obligatori",
                    "Se debe introducir una fecha de cierre");
        } else if (rnas.getStartTime().compareTo(rnas.getEndTime())>=0){
            errors.rejectValue("startTime", "obligatori",
                    "La fecha de inicio no puede ser mas grande que la de fin");
            errors.rejectValue("endTime", "obligatori",
                    "La fecha de inicio no puede ser mas grande que la de fin");
        }
    }
}