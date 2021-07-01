package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.Citizen;
import es.uji.ei1027.proyecto1027.model.Controller;
import es.uji.ei1027.proyecto1027.model.EnvironmentalManager;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EnvironmentalManagerValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Controller.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        EnvironmentalManager environmentalManager = (EnvironmentalManager) obj;
        if (environmentalManager.getNIF().trim().equals(""))
            errors.rejectValue("NIF", "obligatori",
                    "Se debe introducir un valor");
        if (environmentalManager.getPassword().trim().equals(""))
            errors.rejectValue("password", "obligatori",
                    "Se debe introducir una contraseña");
        if (environmentalManager.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Se debe introducir un nombre");
        if (environmentalManager.getSurname().trim().equals(""))
            errors.rejectValue("surname", "obligatori",
                    "Se debe introducir un apellido");
        if (environmentalManager.getEmail().trim().equals(""))
            errors.rejectValue("email", "obligatori",
                    "Se debe introducir un email");
    }
}
