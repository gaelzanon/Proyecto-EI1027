package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.ControllerDao;
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
@RequestMapping("/controller")
public class ControllerController {

    private ControllerDao ControllerDao;

    @Autowired
    public void setControllerDao(ControllerDao controllerDao) {
        this.ControllerDao=controllerDao;
    }

    @RequestMapping("/list")
    public String listController(Model model) {
        model.addAttribute("controller", ControllerDao.getControllers());
        return "controller/list";
    }
    @RequestMapping(value="/add")
    public String addController(Model model) {
        model.addAttribute("controller", new es.uji.ei1027.proyecto1027.model.Controller());
        return "controller/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("controller") es.uji.ei1027.proyecto1027.model.Controller controller,
                                       BindingResult bindingResult) {
        ControllerValidator controllerValidator = new ControllerValidator();
        controllerValidator.validate(controller, bindingResult);
        if (bindingResult.hasErrors())
            return "controller/add";
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
    @RequestMapping(value="/update/{NIF}", method = RequestMethod.GET)
    public String editController(Model model, @PathVariable String NIF) {
        model.addAttribute("controller", ControllerDao.getController(NIF));
        return "controller/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("controller") es.uji.ei1027.proyecto1027.model.Controller controller,
            BindingResult bindingResult) {
        ControllerValidator controllerValidator = new ControllerValidator();
        controllerValidator.validate(controller, bindingResult);
        if (bindingResult.hasErrors())
            return "controller/update";
        ControllerDao.updateController(controller);
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{NIF}")
    public String processDelete(@PathVariable String NIF) {
        ControllerDao.deleteController(NIF);
        return "redirect:../list";
    }






    }

