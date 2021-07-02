package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.MunicipalityManagerDao;
import es.uji.ei1027.proyecto1027.model.*;
import es.uji.ei1027.proyecto1027.services.MunicipalityManagerService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/municipalityManager")
public class MunicipalityManagerController {

    private MunicipalityManagerDao managerDao;
    private MunicipalityManagerService municipalityManagerService;

    //private boolean deUnUso;
    private int codigos;

    @Autowired
    public void setMunicipalityManagerService(MunicipalityManagerService municipalityManagerService) {
        this.municipalityManagerService = municipalityManagerService;
    }

    @Autowired
    public void SetMunicipalityManagerDao(MunicipalityManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @RequestMapping("/list")
    public String listMunicipalityManager(HttpSession session, Model model) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.EnvironmentalManager.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("municipalityManager", managerDao.getMunicipalityManagers());
        return "municipalityManager/list";
    }

    @RequestMapping(value="/add")
    public String addMunicipalityManager(Model model) {
        if(!model.containsAttribute("municipalityManager"))
            model.addAttribute("municipalityManager", new MunicipalityManager());
        List<Municipality> municipiosDisponibles = municipalityManagerService.getAllMunicipalities();
        model.addAttribute("municipiosDisponibles", municipiosDisponibles);
        return "municipalityManager/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("municipalityManager") final MunicipalityManager munManager,
                                   RedirectAttributes attributes, final BindingResult bindingResult) {
        codigos = (int)(Math.random()*100000);
        munManager.setCode( String.valueOf(codigos));
        munManager.setTipoUsuario(UserDetailsEnum.MunicipalManager.toString());
        MunicipalityManagerValidator municipalityManagerValidator = new MunicipalityManagerValidator();
        municipalityManagerValidator.validate(munManager, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.municipalityManager",bindingResult);
            attributes.addFlashAttribute("municipalityManager",munManager);
            return "redirect:/municipalityManager/add";}
        try {
            managerDao.addMunicipalityManager(munManager);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya esta dado de alta el municipal manager "
                            + munManager.getNIF() + " en el municipio "
                            + munManager.getMunicipalityName(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }

    @RequestMapping(value={"/update/{NIF}","/update"}, method= RequestMethod.GET)
    public String editMunicipalityManager(Model model, @PathVariable(required = false) String NIF) {
        if(!model.containsAttribute("municipalityManager"))
            model.addAttribute("municipalityManager", managerDao.getMunicipalityManager(NIF));
        List<Municipality> municipiosDisponibles = municipalityManagerService.getAllMunicipalities();
        model.addAttribute("municipiosDisponibles", municipiosDisponibles);
        return "municipalityManager/update";
    }

    /*@RequestMapping(value="/updatePerfil/{NIF}", method= RequestMethod.GET)
    public String editMunicipalityManagerPerfil(Model model, @PathVariable String NIF) {
        model.addAttribute("municipalityManager", managerDao.getMunicipalityManager(NIF));
        deUnUso=true;
        return "municipalityManager/update";
    }*/


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("municipalityManager") final MunicipalityManager munManager,
            RedirectAttributes attributes, final BindingResult bindingResult) {
        MunicipalityManagerValidator municipalityManagerValidator = new MunicipalityManagerValidator();
        municipalityManagerValidator.validate(munManager, bindingResult);

        /*if (deUnUso==true) {
            deUnUso=false;
            if (bindingResult.hasErrors())
                return "municipalityManager/updatePerfil";

            try {
            managerDao.updateMunicipalityManager(munManager);
            deUnUso=false;
            } catch (DataAccessException e) {
                throw new ProyectoException(
                        "Error en el acceso a la base de datos", "ErrorAccedintDades");
            }
            return "redirect:/mainMenu";
        }*/

        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.municipalityManager",bindingResult);
            attributes.addFlashAttribute("municipalityManager",munManager);
            return "redirect:/municipalityManager/update";}
        try {
            managerDao.updateMunicipalityManager(munManager);
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{NIF}")
    public String processDeleteMunicipalityManager(@PathVariable String NIF) {
        managerDao.deleteMunicipalityManager(NIF);
        return "redirect:../list";
    }

}