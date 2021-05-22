package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ReservationDao;
import es.uji.ei1027.proyecto1027.model.Reservation;
import es.uji.ei1027.proyecto1027.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationDao ReservationDao;

    @Autowired
    public void setReservationDao(ReservationDao ReservationDao) {
        this.ReservationDao=ReservationDao;
    }

    @RequestMapping("/list")
    public String listReservations(HttpSession session, Model model) {
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        model.addAttribute("reservation", ReservationDao.getReservation());
        return "reservation/list";
    }
    @RequestMapping(value="/add")
    public String addReservation(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reservation") Reservation reservation,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "reservation/add";
        try {
            ReservationDao.addReservation(reservation);
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe una reserva para el cliente "
                            + reservation.getNifCitizen() + " en el espacio natural "
                            + reservation.getCode() + " para la fecha "
                            + reservation.getDate(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }
    @RequestMapping(value="/update/{code}", method = RequestMethod.GET)
    public String editReservation(Model model, @PathVariable String code) {
        model.addAttribute("reservation", ReservationDao.getReservation(code));
        return "reservation/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("reservation") Reservation Reservation,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "reservation/update";
        ReservationDao.updateReservation(Reservation);
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{nom}")
    public String processDelete(@PathVariable String code) {
        ReservationDao.deleteReservation(code);
        return "redirect:../list";
    }






}

