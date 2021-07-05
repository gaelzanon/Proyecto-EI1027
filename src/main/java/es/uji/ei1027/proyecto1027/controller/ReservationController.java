package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;

import es.uji.ei1027.proyecto1027.dao.ReservationDao;
import es.uji.ei1027.proyecto1027.dao.UserDao;
import es.uji.ei1027.proyecto1027.model.*;
import es.uji.ei1027.proyecto1027.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    private UserDetails userDetails;

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
        UserDetails user=(UserDetails) session.getAttribute("user");
        if (user == null)
        {
            return "redirect:/";
        } else if(user.getUserType().equals(UserDetailsEnum.Controller.toString())){
            return "redirect:/reservation/porController";
        } else if(user.getUserType().equals(UserDetailsEnum.Citizen.toString())){
            return "redirect:/reservation/porCitizen";
        }
        model.addAttribute("reservation", ReservationDao.getReservation());
        return "reservation/list";
    }
    @RequestMapping(value="/add")
    public String addReservation(Model model) {
        if(!model.containsAttribute("reservation"))
            model.addAttribute("reservation", new Reservation());
        model.addAttribute("codeArea",NaturalAreaDao.getNaturalAreaNames() );
        return "reservation/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reservation") final Reservation reservation,
                                   RedirectAttributes attributes, final BindingResult bindingResult) {

        codigos = (int)(Math.random()*100000);
        reservation.setCode( String.valueOf(codigos));
        reservation.setQr( "q" + (codigos));
        reservation.setState("Confirmada");
        reservation.setCreationDate(LocalDate.now());
        reservation.setAddress(reservation.getCodeArea());
        reservation.setCodeArea(NaturalAreaDao.getNaturalAreaCode(reservation.getCodeArea()));
        ReservationValidator reservationValidator = new ReservationValidator();
        reservationValidator.validate(reservation, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.reservation",bindingResult);
            attributes.addFlashAttribute("reservation",reservation);
            return "redirect:/reservation/add";}
        try {
            ReservationDao.addReservation(reservation);
            return "redirect:reservation/porCitizen";
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
    }
    @RequestMapping(value={"/update/{code}","/update"}, method = RequestMethod.GET)
    public String editReservation(Model model, @PathVariable(required = false) String code) {
        if(!model.containsAttribute("reservation"))
            model.addAttribute("reservation", ReservationDao.getReservation(code));
        model.addAttribute("codeArea",NaturalAreaDao.getNaturalAreaNames() );
        if(!model.containsAttribute("zonas")){
            List<Zone> zonas = reservationService.getAllZonesPerArea(ReservationDao.getReservation(code).getCodeArea());
            model.addAttribute("zonas", zonas);
        }
        return "reservation/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("reservation") final Reservation reservation, @ModelAttribute("zonas") final ArrayList<Zone> zonas,
            RedirectAttributes attributes, final BindingResult bindingResult) {
        reservation.setCodeArea(NaturalAreaDao.getNaturalAreaCode(reservation.getCodeArea()));
        ReservationValidator reservationValidator = new ReservationValidator();
        reservationValidator.validate(reservation, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.reservation",bindingResult);
            attributes.addFlashAttribute("reservation",reservation);
            attributes.addFlashAttribute("zonas",zonas);
            return "redirect:/reservation/update"; }
        try {
            ReservationDao.updateReservation(reservation);
            return "redirect:list";
        } catch (DataAccessException e) {
        throw new ProyectoException(
                "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }

    }

    @RequestMapping(value="/details/{code}", method = RequestMethod.GET)
    public String detailsReservation(Model model, @PathVariable String code) {
        Reservation reservation = ReservationDao.getReservation(code);
        model.addAttribute("reservation", reservation);
        Zone zone = reservationService.getZone(reservation.getCodeZone());
        String coord = "Fila: " + zone.getRow() + ", Columna:" + zone.getCol();
        model.addAttribute("coord", coord);
        NaturalArea natArea = reservationService.getNaturalArea(reservation.getCodeArea());
        Municipality municipio = reservationService.getMunicipioDeNaturalArea(natArea.getMunCode());
        Citizen citizen = reservationService.getCitizen(reservation.getNifCitizen());
        model.addAttribute("citizen", citizen);
        model.addAttribute("municipio", municipio);
        model.addAttribute("natArea", natArea);
        return "reservation/details";
    }

    @RequestMapping(value="/delete/{code}")
    public String processDelete(@PathVariable String code) {
        try{
            ReservationDao.deleteReservation(code);
            return "redirect:/reservation/list";
        } catch (Exception e) {
            throw new ProyectoException(
                    "Error en la base de datos.", "ErrorAccedintDades");
        }

    }

    @RequestMapping(value={"/detailsCitizen/{code}","/detailsCitizen"}, method = RequestMethod.GET)
    public String detailsCitizenReservation(Model model, @PathVariable(required = false) String code) {
        if(!model.containsAttribute("reservation"))
            model.addAttribute("reservation", ReservationDao.getReservation(code));
        Zone zone = reservationService.getZone(ReservationDao.getReservation(code).getCodeZone());
        //String coord = "(" + zone.getCol() + ", " + zone.getRow() + ")";
        //model.addAttribute("coord", coord);
        return "reservation/detailsCitizen";
    }



    @RequestMapping(value = {"/porArea/{code_area}","/porArea"}, method = RequestMethod.GET)
    public String reservationPorArea(Model model,@PathVariable(required = false) String code_area, HttpSession session) {
        Reservation reservation = new Reservation();

        reservation.setCodeArea(code_area);
        if(!model.containsAttribute("reservation"))
            model.addAttribute("reservation", reservation);

        String nombre = reservationService.getAreaName(code_area);
        model.addAttribute("nombreArea", nombre);

        String nif = session.getAttribute("nif").toString();
        model.addAttribute("citizen", reservationService.getCitizen(nif));

        List<Zone> zonas = reservationService.getAllZonesPerArea(code_area);
        model.addAttribute("zonas", zonas);
        return "reservation/porArea";
    }

    @RequestMapping(value="/porArea", method=RequestMethod.POST)
    public String processAddSubmitPorArea(@ModelAttribute("reservation") final Reservation reservation, RedirectAttributes attributes,
                                          final BindingResult bindingResult, HttpSession session) {
//        String[] coord = coordenadas.split(",");
//        reservation.setCols(Integer.parseInt(coord[0]));
//        reservation.setRow(Integer.parseInt(coord[1]));
        String nif = session.getAttribute("nif").toString();
        reservation.setNifCitizen(nif);
        codigos = (int)(Math.random()*100000);
        reservation.setCode( String.valueOf(codigos));
        reservation.setQr( "q" + (codigos));
        reservation.setState("Confirmada");
        reservation.setCreationDate(LocalDate.now());
        reservation.setAddress(reservationService.getAddress(reservation.getCodeArea()));
        String nameUri="redirect:porArea/" + reservation.getCodeArea();
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        ReservationValidator reservationValidator = new ReservationValidator();
        reservationValidator.validate(reservation, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.reservation",bindingResult);
            attributes.addFlashAttribute("reservation",reservation);
            return nameUri; }
        try {
            ReservationDao.addReservation(reservation);
            return "redirect:porCitizen";
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

    }

    @RequestMapping("/porController")
    public String reservationListPorController(Model model, HttpSession session) {
        String nifController = ((UserDetails) session.getAttribute("user")).getNIF();
        String area = reservationService.getController(nifController).getCode_area();
        List<Reservation> reservas = ReservationDao.getReservationPerNaturalArea(area);
        model.addAttribute("reservas", reservas);
        return "reservation/porController";
    }

    @RequestMapping("/porCitizen")
    public String reservationPorCitizen(HttpSession session, Model model) {
        UserDetails userDetails=(UserDetails) session.getAttribute("user");
        List<Reservation> reservas = ReservationDao.getReservationPerCitizen(userDetails.getNIF());
        model.addAttribute("reservas", reservas);
        return "reservation/porCitizen";
    }

    @RequestMapping("porMunicipalManager")
    public String reservationListPorMunicipalManager(Model model, HttpSession session) {
        String nif = ((UserDetails) session.getAttribute("user")).getNIF();
        List<String> areas = reservationService.getAreasForMunicipalManager(nif);
        List<Reservation> reservasPorMunicipio = new ArrayList<Reservation>();
        for (String area : areas) {
            reservasPorMunicipio.addAll(ReservationDao.getReservationPerNaturalArea(area));
        }
        model.addAttribute("reservas", reservasPorMunicipio);
        return "reservation/porMunicipalManager";
    }

    @RequestMapping("/addArea")
    public String reservationAddArea(HttpSession session, Model model) {
        UserDetails userDetails=(UserDetails) session.getAttribute("user");
        List<NaturalArea> naturalArea = NaturalAreaDao.getNaturalArea();
        model.addAttribute("naturalArea", naturalArea);
        return "reservation/addArea";
    }
}

