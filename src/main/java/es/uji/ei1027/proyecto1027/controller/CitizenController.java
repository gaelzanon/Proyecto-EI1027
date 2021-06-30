package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.CitizenDao;
import es.uji.ei1027.proyecto1027.model.Citizen;
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
    public String listCitizens(Model model) {
        model.addAttribute("citizen", CitizenDao.getCitizens());
        return "citizen/list";
    }
    @RequestMapping(value="/add")
    public String addCitizen(Model model) {
        model.addAttribute("citizen", new Citizen());
        return "citizen/add";
    }
    @RequestMapping(value="/addRegistro")
    public String addCitizenRegistro(Model model) {
        model.addAttribute("citizen", new Citizen());
        deUnUso=true;
        return "citizen/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("citizen") Citizen citizen,
                                   BindingResult bindingResult) {
        citizen.setTipoUsuario("citizen");
        CitizenValidator citizenValidator = new CitizenValidator();
        citizenValidator.validate(citizen, bindingResult);
        if (deUnUso==true) {
            if (bindingResult.hasErrors())
                return "citizen/add";
            deUnUso=false;
            CitizenDao.updateCitizen(citizen);
            return "citizen/Registrado";
        }
        if (bindingResult.hasErrors())
            return "citizen/add";
        try {
            CitizenDao.addCitizen(citizen);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el usuario "
                            + citizen.getNIF(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }

    @RequestMapping(value="/update/{NIF}", method = RequestMethod.GET)
    public String editCitizen(Model model, @PathVariable String NIF) {
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
            @ModelAttribute("citizen") Citizen citizen,
            BindingResult bindingResult) {
        CitizenValidator citizenValidator = new CitizenValidator();
        citizenValidator.validate(citizen, bindingResult);
        if (deUnUso==true) {
            if (bindingResult.hasErrors())
                return "citizen/update";
            deUnUso=false;
            CitizenDao.updateCitizen(citizen);
            return "redirect:/mainMenu";
        }
        if (bindingResult.hasErrors())
            return "citizen/update";
        CitizenDao.updateCitizen(citizen);
        return "redirect:../../../";
    }

    @RequestMapping(value="/delete/{NIF}")
    public String processDelete(@PathVariable String NIF) {
        CitizenDao.deleteCitizen(NIF);
        return "redirect:../list";
    }





}

