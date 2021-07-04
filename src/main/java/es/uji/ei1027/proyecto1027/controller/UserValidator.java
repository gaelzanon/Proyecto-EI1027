package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return UserDetails.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        UserDetails userDetails = (UserDetails) obj;
        if (userDetails.getUsername().trim().equals("")) {
            errors.rejectValue("username", "obligatori", "Introduce tu NIF");
        }
        if (userDetails.getPassword().trim().equals("")) {
            errors.rejectValue("password", "obligatori", "Se debe introducir una contraseña");
        }
    }
}
