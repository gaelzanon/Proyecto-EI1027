package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ReservationDao;
import es.uji.ei1027.proyecto1027.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/Reservation")
public class ReservationController {

    private ReservationDao ReservationDao;

    @Autowired
    public void setReservationDao(ReservationDao ReservationDao) {
        this.ReservationDao=ReservationDao;
    }

    @RequestMapping("/list")
    public String listReservations(Model model) {
        model.addAttribute("Reservation", ReservationDao.getReservation());
        return "Reservation/list";
    }
    @RequestMapping(value="/add")
    public String addReservation(Model model) {
        model.addAttribute("Reservation", new Reservation());
        return "Reservation/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("Reservation") Reservation Reservation,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Reservation/add";
        ReservationDao.addReservation(Reservation);
        return "redirect:list";
    }
    @RequestMapping(value="/update/{nom}", method = RequestMethod.GET)
    public String editReservation(Model model, @PathVariable String code) {
        model.addAttribute("Reservation", ReservationDao.getReservation(code));
        return "Reservation/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("Reservation") Reservation Reservation,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Reservation/update";
        ReservationDao.updateReservation(Reservation);
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{nom}")
    public String processDelete(@PathVariable String code) {
        ReservationDao.deleteReservation(code);
        return "redirect:../list";
    }






}

