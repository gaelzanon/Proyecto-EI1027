package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.CitizenDao;
import es.uji.ei1027.proyecto1027.model.Citizen;
import es.uji.ei1027.proyecto1027.model.UserDetails;
import es.uji.ei1027.proyecto1027.model.UserDetailsEnum;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/citizen")
public class CitizenController {

    private CitizenDao CitizenDao;
    private boolean deUnUso;
    @Autowired
    public void setCitizenDao(CitizenDao citizenDao) {
        this.CitizenDao=citizenDao;
    }

    @RequestMapping("/list")
    public String listCitizens(HttpSession session,Model model) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.MunicipalManager.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("citizen", CitizenDao.getCitizens());
        return "citizen/list";
    }
    @RequestMapping(value="/add")
    public String addCitizen(Model model) {
        if(!model.containsAttribute("citizen"))
            model.addAttribute("citizen", new Citizen());
        return "citizen/add";
    }
    @RequestMapping(value="/addRegistro")
    public String addCitizenRegistro(Model model) {
        if(!model.containsAttribute("citizen"))
            model.addAttribute("citizen", new Citizen());
        deUnUso=true;
        return "citizen/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("citizen") final Citizen citizen,
                                   RedirectAttributes attributes, final BindingResult bindingResult) {
        citizen.setTipoUsuario("citizen");
        CitizenValidator citizenValidator = new CitizenValidator();
        citizenValidator.validate(citizen, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.citizen",bindingResult);
            attributes.addFlashAttribute("citizen",citizen);
            return "redirect:/citizen/add"; }
        try {
            CitizenDao.addCitizen(citizen);
            return "citizen/Registrado";
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el usuario "
                            + citizen.getNIF(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }

    }

    @RequestMapping(value={"/update/{NIF}","/update"}, method = RequestMethod.GET)
    public String editCitizen(Model model, @PathVariable(required = false) String NIF) {
        if(!model.containsAttribute("citizen"))
            model.addAttribute("citizen", CitizenDao.getCitizen(NIF));
        return "citizen/update";
    }
    @RequestMapping(value="/updatePerfil/{NIF}", method = RequestMethod.GET)
    public String editCitizenPerfil(Model model, @PathVariable String NIF) {
        model.addAttribute("citizen", CitizenDao.getCitizen(NIF));

        deUnUso=true;
        return "citizen/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("citizen") final Citizen citizen,
            RedirectAttributes attributes, final BindingResult bindingResult) {
        CitizenValidator citizenValidator = new CitizenValidator();
        citizenValidator.validate(citizen, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.citizen",bindingResult);
            attributes.addFlashAttribute("citizen",citizen);
            return "redirect:/citizen/update"; }
        try{
            CitizenDao.updateCitizen(citizen);
            return "redirect:/mainMenu";
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
    }

    @RequestMapping(value="/delete/{NIF}")
    public String processDelete(@PathVariable String NIF) {
        try{
            CitizenDao.deleteCitizen(NIF);
            return "redirect:../list";
        } catch (Exception e){
            throw new ProyectoException(
                    "Ha ocurrido un error accediendo a la base de datos. Intentalo de nuevo mas tarde.", "ErrorAccedintDades");
        }
    }





}

