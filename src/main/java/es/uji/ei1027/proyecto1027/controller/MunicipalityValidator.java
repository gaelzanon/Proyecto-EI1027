package es.uji.ei1027.proyecto1027.controller;

        import es.uji.ei1027.proyecto1027.model.Controller;
        import es.uji.ei1027.proyecto1027.model.Municipality;
        import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
        import org.springframework.validation.Errors;
        import org.springframework.validation.Validator;

public class MunicipalityValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Municipality.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Municipality municipality = (Municipality) obj;
        if (municipality.getCode().trim().equals(""))
            errors.rejectValue("Code", "obligatori",
                    "Se debe introducir un Codigo");
        if (municipality.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Se debe introducir un nombre para el municipio");
        if (municipality.getRegistrationDate()==null)
            errors.rejectValue("registrationDate", "obligatori",
                    "Se debe introducir un Fecha de Registro");
    }
}