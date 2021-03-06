package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ServiceDao;
import es.uji.ei1027.proyecto1027.dao.TypeServiceDao;
import es.uji.ei1027.proyecto1027.model.Service;
import es.uji.ei1027.proyecto1027.model.TypeService;
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
import java.util.List;

@Controller
@RequestMapping("/typeService")
public class TypeServiceController {

    private TypeServiceDao typeServiceDao;

    @Autowired
    public void setTypeServiceDao(TypeServiceDao typeServiceDao) {
        this.typeServiceDao=typeServiceDao;
    }


    @RequestMapping("/list")
    public String listTypeServices(HttpSession session, Model model) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.EnvironmentalManager.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("type_of_service", typeServiceDao.getTypeServices());
        return "typeService/list";
    }
    @RequestMapping(value="/add")
    public String addTypeService(Model model) {
        if(!model.containsAttribute("type_of_service"))
            model.addAttribute("type_of_service", new TypeService());
        return "typeService/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("type_of_service") final TypeService typeService,
                                   RedirectAttributes attributes, final BindingResult bindingResult) {
        TypeServiceValidator typeServiceValidator = new TypeServiceValidator();
        typeServiceValidator.validate(typeService, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.type_of_service",bindingResult);
            attributes.addFlashAttribute("type_of_service",typeService);
            return "redirect:/typeService/add";}
        try {
            typeServiceDao.addTypeService(typeService);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el tipo de servicio "
                            + typeService.getType(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }


    @RequestMapping(value="/delete/{type}")
    public String processDelete(@PathVariable String type) {

        try{
            typeServiceDao.deleteTypeService(type);
            return "redirect:../list";
        } catch (Exception e){
            throw new ProyectoException(
                    "Lo sentimos pero existen servicios con este tipo de servicio asignado.", "ErrorAccedintDades");
        }

    }






}

