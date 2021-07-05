package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.ControllerDao;
import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
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
@RequestMapping("/controller")
public class ControllerController {
    boolean deUnUso=false;
    private ControllerDao ControllerDao;
    private NaturalAreaDao naturalAreaDao;

    @Autowired
    public void setControllerDao(ControllerDao controllerDao) {
        this.ControllerDao=controllerDao;
    }

    @Autowired
    public void setNaturalAreaDao(NaturalAreaDao naturalAreaDao) {
        this.naturalAreaDao=naturalAreaDao;
    }

    @RequestMapping("/list")
    public String listController(HttpSession session, Model model) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.MunicipalManager.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("controller", ControllerDao.getControllers());
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        return "controller/list";
    }
    @RequestMapping(value="/add")
    public String addController(Model model) {
        if(!model.containsAttribute("controller"))
            model.addAttribute("controller", new es.uji.ei1027.proyecto1027.model.Controller());
        model.addAttribute("code_area",naturalAreaDao.getNaturalAreaNames());
        return "controller/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("controller") final es.uji.ei1027.proyecto1027.model.Controller controller, RedirectAttributes attributes,
                                       final BindingResult bindingResult) {
        controller.setCode_area(naturalAreaDao.getNaturalAreaCode(controller.getCode_area()));
        controller.setTipoUsuario("controller");
        ControllerValidator controllerValidator = new ControllerValidator();
        controllerValidator.validate(controller, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.controller",bindingResult);
            attributes.addFlashAttribute("controller",controller);
            return "redirect:/controller/add"; }
        try {
            ControllerDao.addController(controller);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el controlador "
                            + controller.getNIF(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }
    @RequestMapping(value={"/update/{NIF}","/update"}, method = RequestMethod.GET)
    public String editController(Model model, @PathVariable(required = false) String NIF) {
        if(!model.containsAttribute("controller"))
            model.addAttribute("controller", ControllerDao.getController(NIF));
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        return "controller/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("controller") final es.uji.ei1027.proyecto1027.model.Controller controller,
            RedirectAttributes attributes, final BindingResult bindingResult) {
        ControllerValidator controllerValidator = new ControllerValidator();
        controllerValidator.validate(controller, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.controller",bindingResult);
            attributes.addFlashAttribute("controller",controller);
            return "redirect:/controller/update"; }

        try{
            ControllerDao.updateController(controller);
            return "redirect:list";
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }

    }
    @RequestMapping(value="/updatePerfil/{NIF}", method = RequestMethod.GET)
    public String editControllerPerfil(Model model, @PathVariable String NIF) {
        model.addAttribute("controller", ControllerDao.getController(NIF));
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        deUnUso=true;
        return "controller/update";
    }

    @RequestMapping(value="/delete/{NIF}")
    public String processDelete(@PathVariable String NIF) {
        try{
            ControllerDao.deleteController(NIF);
            return "redirect:../list";
        } catch (Exception e){
            throw new ProyectoException(
                    "Ha ocurrido un error accediendo a la base de datos. Intentalo de nuevo mas tarde.", "ErrorAccedintDades");
        }
    }






    }

