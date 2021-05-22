package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.Citizen;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CitizenValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Citizen.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Citizen citizen = (Citizen) obj;
        if (citizen.getNIF().trim().equals(""))
            errors.rejectValue("NIF", "obligatori",
                    "Se debe introducir un valor");
        if (citizen.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Se debe introducir un nombre");
        if (citizen.getSurname().trim().equals(""))
            errors.rejectValue("surname", "obligatori",
                    "Se debe introducir un apellido");
        if (citizen.getAddress().trim().equals(""))
            errors.rejectValue("address", "obligatori",
                    "Se debe introducir una dirección domiciliaria");
        if (citizen.getEmail().trim().equals(""))
            errors.rejectValue("Email", "obligatori",
                    "Se debe introducir un email");
    }
}
