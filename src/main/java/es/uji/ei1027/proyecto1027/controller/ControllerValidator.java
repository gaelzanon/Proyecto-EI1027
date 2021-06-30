package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.Citizen;
import es.uji.ei1027.proyecto1027.model.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ControllerValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Controller.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Controller controller = (Controller) obj;
        if (controller.getNIF().trim().equals(""))
            errors.rejectValue("NIF", "obligatori",
                    "Se debe introducir un valor");
        if (controller.getPassword().trim().equals(""))
            errors.rejectValue("password", "obligatori",
                    "Se debe introducir una contraseña");
        if (controller.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Se debe introducir un nombre");
        if (controller.getSurname().trim().equals(""))
            errors.rejectValue("surname", "obligatori",
                    "Se debe introducir un apellido");
        if (controller.getCode_area().trim().equals(""))
            errors.rejectValue("code_area", "obligatori",
                    "Se debe introducir un código de area");
        if (controller.getEmail().trim().equals(""))
            errors.rejectValue("email", "obligatori",
                    "Se debe introducir un email");
    }
}
