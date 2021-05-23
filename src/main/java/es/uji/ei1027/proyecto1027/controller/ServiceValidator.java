package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ServiceValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Service.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Service service = (Service) obj;
        if (service.getCode().trim().equals(""))
            errors.rejectValue("code", "obligatori",
                    "Se debe introducir un valor");
        if (service.getDescription().trim().equals(""))
            errors.rejectValue("description", "obligatori",
                    "Se debe introducir una descripcion");
        if (service.getType_of_service().trim().equals(""))
            errors.rejectValue("type_of_service","obligatori","Se debe asignar un tipo");
    }
}
