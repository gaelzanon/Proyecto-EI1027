package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;

import es.uji.ei1027.proyecto1027.dao.ReservationDao;
import es.uji.ei1027.proyecto1027.model.Citizen;
import es.uji.ei1027.proyecto1027.model.Reservation;
import es.uji.ei1027.proyecto1027.model.UserDetails;
import es.uji.ei1027.proyecto1027.model.Zone;
import es.uji.ei1027.proyecto1027.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.naming.Binding;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private NaturalAreaDao NaturalAreaDao;
    private ReservationDao ReservationDao;
    private ReservationService reservationService;
    private int codigos;

    @Autowired
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Autowired
    public void setReservationDao(ReservationDao ReservationDao) {
        this.ReservationDao=ReservationDao;
    }

    @Autowired
    public void setnaturalAreaDao(NaturalAreaDao NaturalAreaDao) {
        this.NaturalAreaDao=NaturalAreaDao;
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
        model.addAttribute("codeArea",NaturalAreaDao.getNaturalAreaNames() );
        return "reservation/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reservation") Reservation reservation,
                                   BindingResult bindingResult) {

        codigos = (int)(Math.random()*100000);
        reservation.setCode( String.valueOf(codigos));
        reservation.setQr( "q" + (codigos));
        reservation.setState("Confirmada");
        reservation.setCreationDate(LocalDate.now());
        reservation.setAddress(reservation.getCodeArea());
        reservation.setCodeArea(NaturalAreaDao.getNaturalAreaCode(reservation.getCodeArea()));
        ReservationValidator reservationValidator = new ReservationValidator();
        reservationValidator.validate(reservation, bindingResult);
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
        return "redirect:reservation/porCitizen";
    }
    @RequestMapping(value="/update/{code}", method = RequestMethod.GET)
    public String editReservation(Model model, @PathVariable String code) {
        model.addAttribute("reservation", ReservationDao.getReservation(code));
        model.addAttribute("codeArea",NaturalAreaDao.getNaturalAreaNames() );
        return "reservation/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("reservation") Reservation reservation,
            BindingResult bindingResult) {
        reservation.setCodeArea(NaturalAreaDao.getNaturalAreaCode(reservation.getCodeArea()));
        System.out.println(reservation);
        if (bindingResult.hasErrors())
            return "reservation/update";
        ReservationValidator reservationValidator = new ReservationValidator();
        reservationValidator.validate(reservation, bindingResult);

        ReservationDao.updateReservation(reservation);
        return "redirect:list";
    }
    @RequestMapping(value={"/details/{code}","/details"}, method = RequestMethod.GET)
    public String detailsReservation(Model model, @PathVariable(required = false) String code) {
        if(!model.containsAttribute("reservation"))
            model.addAttribute("reservation", ReservationDao.getReservation(code));
        return "reservation/details";
    }

    @RequestMapping(value="/delete/{code}")
    public String processDelete(@PathVariable String code) {
        ReservationDao.deleteReservation(code);
        return "redirect:../porCitizen";
    }

    @RequestMapping(value={"/detailsCitizen/{code}","/detailsCitizen"}, method = RequestMethod.GET)
    public String detailsCitizenReservation(Model model, @PathVariable(required = false) String code) {
        if(!model.containsAttribute("reservation"))
            model.addAttribute("reservation", ReservationDao.getReservation(code));
        return "reservation/detailsCitizen";
    }



    @RequestMapping("/porArea/{code_area}")
    public String reservationPorArea(Model model, @PathVariable String code_area) {
        Reservation reservation = new Reservation();
        reservation.setCodeArea(code_area);
        model.addAttribute("codeArea", reservation);
        List<Zone> zonas = reservationService.getAllZonesPerArea(code_area);
        List<String> col_row = new ArrayList<>();
        LinkedHashSet<Integer> col = new LinkedHashSet<>();
        LinkedHashSet<Integer> row = new LinkedHashSet<>();
        for (Zone zona: zonas) {
            col_row.add("(" + zona.getRow() + "," + zona.getCol() + ")");
            col.add(zona.getRow());
            row.add(zona.getCol());
        }
        model.addAttribute("zonas", col_row);
        model.addAttribute("cols", col);
        model.addAttribute("rows", row);
        return "reservation/porArea";
    }

    @RequestMapping(value="/porArea", method=RequestMethod.POST)
    public String processAddSubmitPorArea(@ModelAttribute("reservation") Reservation reservation, BindingResult bindingResult) {
//        System.out.println(coordenadas);
//        String[] coord = coordenadas.split(",");
//        reservation.setCols(Integer.parseInt(coord[0]));
//        reservation.setRow(Integer.parseInt(coord[1]));
        codigos = (int)(Math.random()*100000);
        reservation.setCode( String.valueOf(codigos));
        reservation.setQr( "q" + (codigos));
        reservation.setState("Confirmada");
        reservation.setCreationDate(LocalDate.now());
        reservation.setAddress(reservationService.getAddress(reservation.getCodeArea()));
        String nameUri="redirect:porArea/" + reservation.getCodeArea();
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        if(bindingResult.hasErrors()){
            return nameUri;
        }
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
        return "redirect:porCitizen";
    }

    @RequestMapping("/porCitizen")
    public String reservationPorCitizen(HttpSession session, Model model) {
        UserDetails userDetails=(UserDetails) session.getAttribute("user");
        List<Reservation> reservas = ReservationDao.getReservationPerCitizen(userDetails.getNIF());
        model.addAttribute("reservas", reservas);
        return "reservation/porCitizen";
    }

}

