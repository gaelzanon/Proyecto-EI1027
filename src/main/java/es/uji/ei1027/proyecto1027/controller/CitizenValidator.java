package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.Citizen;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CitizenValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Citizen.class.equals(cls);

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
        if (citizen.getDate_of_birth() == null) {
            errors.rejectValue("date_of_birth", "obligatori",
                    "Se debe introducir una fecha de nacimiento");
        }
        if (citizen.getAddress().trim().equals(""))
            errors.rejectValue("Address", "obligatori",
                    "Se debe introducir una dirección domiciliaria");
        if (citizen.getEmail().trim().equals(""))
            errors.rejectValue("Email", "obligatori",
                    "Se debe introducir un email");
        if (citizen.getPassword().trim().equals(""))
            errors.rejectValue("password", "obligatori",
                    "Se debe introducir una contraseña");
    }
}
