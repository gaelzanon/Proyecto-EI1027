package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ResNatAreaServiceValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return ResNatAreaService.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ResNatAreaService rnas = (ResNatAreaService) obj;
        if(rnas.getCode().trim().equals("NoDisp"))
            errors.rejectValue("code", "obligatori",
                    "No existen más servicios disponibles");
        if (rnas.getCode_area().trim().equals(""))
            errors.rejectValue("code_area", "obligatori",
                    "Se debe introducir un valor");
        if (rnas.getCode().trim().equals(""))
            errors.rejectValue("code", "obligatori",
                    "Se debe introducir un valor");
    }
}
