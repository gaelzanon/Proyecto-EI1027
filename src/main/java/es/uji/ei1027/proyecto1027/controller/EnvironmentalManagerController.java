package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.ControllerDao;
import es.uji.ei1027.proyecto1027.dao.EnvironmentalManagerDao;
import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.model.EnvironmentalManager;
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
@RequestMapping("/environmentalManager")
public class EnvironmentalManagerController {
    boolean deUnUso=false;
    private ControllerDao ControllerDao;
    private NaturalAreaDao naturalAreaDao;
    private EnvironmentalManagerDao environmentalManagerDao;

    @Autowired
    public void setControllerDao(ControllerDao controllerDao) {
        this.ControllerDao=controllerDao;
    }

    @Autowired
    public void setNaturalAreaDao(NaturalAreaDao naturalAreaDao) {
        this.naturalAreaDao=naturalAreaDao;
    }

    @Autowired
    public void setEnvironmentalManagerDao(EnvironmentalManagerDao environmentalManagerDao) {
        this.environmentalManagerDao=environmentalManagerDao;
    }

    @RequestMapping("/list")
    public String listEnvironmentalManager(HttpSession session, Model model) {
        //return "redirect:/"; //nadie accede a la lista de environmental managers
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.Admin.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("environmentalManager", environmentalManagerDao.getEnvironmentalManagers());
        model.addAttribute("controller", ControllerDao.getControllers());
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        return "environmentalManager/list";
    }
    @RequestMapping(value="/add")
    public String addEnvironmentalManager(Model model) {
        if(!model.containsAttribute("environmentalManager"))
            model.addAttribute("environmentalManager", new EnvironmentalManager());

        return "environmentalManager/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("environmentalManager") final EnvironmentalManager environmentalManager, RedirectAttributes attributes,
                                   final BindingResult bindingResult) {

        environmentalManager.setTipoUsuario("EnvironmentalManager");
        EnvironmentalManagerValidator environmentalManagerValidator = new EnvironmentalManagerValidator();
        environmentalManagerValidator.validate(environmentalManager, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.environmentalManager",bindingResult);
            attributes.addFlashAttribute("environmentalManager",environmentalManager);
            return "redirect:/environmentalManager/add"; }
        try {
            environmentalManagerDao.addEnvironmentalManager(environmentalManager);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el controlador "
                            + environmentalManager.getNIF(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }
    @RequestMapping(value={"/update/{NIF}","/update"}, method = RequestMethod.GET)
    public String editEnvironmentalManager(Model model, @PathVariable(required = false) String NIF) {
        if(!model.containsAttribute("environmentalManager"))
            model.addAttribute("environmentalManager", environmentalManagerDao.getEnvironmentalManager(NIF));

        return "environmentalManager/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("environmentalManager") final EnvironmentalManager environmentalManager, RedirectAttributes attributes,
            final BindingResult bindingResult) {
        EnvironmentalManagerValidator environmentalManagerValidator = new EnvironmentalManagerValidator();
        environmentalManagerValidator.validate(environmentalManager, bindingResult);

        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.environmentalManager",bindingResult);
            attributes.addFlashAttribute("environmentalManager",environmentalManager);
            return "redirect:/environmentalManager/update";}

        try{
            environmentalManagerDao.updateEnvironmentalManager(environmentalManager);
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }

        return "redirect:list";
    }
    @RequestMapping(value="/updatePerfil/{NIF}", method = RequestMethod.GET)
    public String editControllerPerfil(Model model, @PathVariable String NIF) {
        model.addAttribute("environmentalManager", environmentalManagerDao.getEnvironmentalManager(NIF));
        deUnUso=true;
        return "environmentalManager/update";
    }

    @RequestMapping(value="/delete/{NIF}")
    public String processDelete(@PathVariable String NIF) {
        try{
            environmentalManagerDao.deleteEnvironmentalManager(NIF);
            return "redirect:../list";
        } catch (Exception e){
            throw new ProyectoException(
                    "Ha ocurrido un error accediendo a la base de datos. Intentalo de nuevo mas tarde.", "ErrorAccedintDades");
        }
    }






}