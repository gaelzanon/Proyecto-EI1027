package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.model.Controller;
import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MunicipalityManagerValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return MunicipalityManager.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        MunicipalityManager municipalityManager = (MunicipalityManager) obj;
        if (municipalityManager.getNIF().trim().equals(""))
            errors.rejectValue("NIF", "obligatori",
                    "Se debe introducir un valor");
        if (municipalityManager.getCode().trim().equals(""))
            errors.rejectValue("code", "obligatori",
                    "Se debe introducir un codigo");
        if (municipalityManager.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Se debe introducir un nombre");
        if (municipalityManager.getMunicipalityName().trim().equals(""))
            errors.rejectValue("municipalityName", "obligatori",
                    "Se debe introducir un nombre de municipalidad");
        if (municipalityManager.getEmail().trim().equals(""))
            errors.rejectValue("email", "obligatori",
                    "Se debe introducir un email");
    }
}
