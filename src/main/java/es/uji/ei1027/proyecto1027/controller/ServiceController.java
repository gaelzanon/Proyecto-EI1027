package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ServiceDao;
import es.uji.ei1027.proyecto1027.dao.TypeServiceDao;
import es.uji.ei1027.proyecto1027.model.Service;
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

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceController {

    private ServiceDao ServiceDao;
    private TypeServiceDao typeServiceDao;

    @Autowired
    public void setServiceDao(ServiceDao ServiceDao) {
        this.ServiceDao=ServiceDao;
    }
    @Autowired
    public void setTypeServiceDao(TypeServiceDao typeServiceDao) {
        this.typeServiceDao=typeServiceDao;
    }

    @RequestMapping("/list")
    public String listServices(Model model) {
        model.addAttribute("service", ServiceDao.getServices());
        System.out.println(ServiceDao.getServices());
        return "service/list";
    }
    @RequestMapping(value="/add")
    public String addService(Model model) {
        model.addAttribute("service", new Service());
        model.addAttribute("type_of_service", typeServiceDao.getTypeServices());
        return "service/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("service") Service service,
                                   BindingResult bindingResult) {
        ServiceValidator serviceValidator = new ServiceValidator();
        serviceValidator.validate(service, bindingResult);
        if (bindingResult.hasErrors())
            return "service/add";
        try {
            ServiceDao.addService(service);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el servicio "
                            + service.getCode(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }

    @RequestMapping(value="/update/{code}", method = RequestMethod.GET)
    public String editService(Model model, @PathVariable String code) {
        model.addAttribute("service", ServiceDao.getService(code));
        model.addAttribute("type_of_service", typeServiceDao.getTypeServices());
        return "service/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("service") Service service,
            BindingResult bindingResult) {
        ServiceValidator serviceValidator = new ServiceValidator();
        serviceValidator.validate(service, bindingResult);
        if (bindingResult.hasErrors())
            return "service/update";
        try {
            ServiceDao.updateService(service);
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{code}")
    public String processDelete(@PathVariable String code) {
        ServiceDao.deleteService(code);
        return "redirect:../list";
    }






}

