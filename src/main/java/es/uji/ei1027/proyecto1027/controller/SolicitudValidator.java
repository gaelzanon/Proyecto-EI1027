package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.Solicitud;
import es.uji.ei1027.proyecto1027.model.TypeNaturalArea;
import es.uji.ei1027.proyecto1027.model.TypeService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SolicitudValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Solicitud.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Solicitud Solicitud = (Solicitud) obj;
        if (Solicitud.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Se debe introducir un nombre");
        if (Solicitud.getDescripcion().trim().equals(""))
            errors.rejectValue("descripcion", "obligatori",
                    "Se debe introducir una descripcion");
    }
}