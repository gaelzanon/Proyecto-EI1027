package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.Service;
import es.uji.ei1027.proyecto1027.model.TypeService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TypeServiceValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return TypeService.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        TypeService typeService = (TypeService) obj;
        if (typeService.getType().trim().equals(""))
            errors.rejectValue("type", "obligatori",
                    "Se debe introducir un valor");
    }
}
