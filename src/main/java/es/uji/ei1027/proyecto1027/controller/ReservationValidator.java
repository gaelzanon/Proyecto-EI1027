package es.uji.ei1027.proyecto1027.controller;

        import es.uji.ei1027.proyecto1027.model.Reservation;
        import es.uji.ei1027.proyecto1027.model.Service;
        import org.springframework.validation.Errors;
        import org.springframework.validation.Validator;

public class ReservationValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Reservation.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Reservation reservation = (Reservation) obj;
        if (reservation.getQr().trim().equals(""))
            errors.rejectValue("qr","obligatori",
                    "Se debe asignar un qr");
        if (reservation.getNifCitizen().trim().equals(""))
            errors.rejectValue("nifCitizen", "obligatori",
                    "Se debe introducir un nifCitizen valido");
        if (reservation.getState().trim().equals(""))
            errors.rejectValue("state", "obligatori",
                    "Se debe introducir un state");
        if (reservation.getStartTime()==null || reservation.getEndTime()==null) {
            errors.rejectValue("startTime", "obligatori",
                    "Se debe introducir una fecha de apertura");
            errors.rejectValue("endTime", "obligatori",
                    "Se debe introducir una fecha de cierre");
        } else if (reservation.getStartTime().compareTo(reservation.getEndTime())>=0){
            errors.rejectValue("startTime", "obligatori",
                    "La fecha de inicio no puede ser mas grande que la de fin");
            errors.rejectValue("endTime", "obligatori",
                    "La fecha de inicio no puede ser mas grande que la de fin");
        }
        if (reservation.getDate()==null)
            errors.rejectValue("date", "obligatori",
                    "Se debe introducir una fecha para la reserva");
    }
}
