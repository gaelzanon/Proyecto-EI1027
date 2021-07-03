package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.dao.ServiceDao;
import es.uji.ei1027.proyecto1027.dao.TypeServiceDao;
import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.model.Service;
import es.uji.ei1027.proyecto1027.model.UserDetails;
import es.uji.ei1027.proyecto1027.model.UserDetailsEnum;
import es.uji.ei1027.proyecto1027.services.ServiceService;
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
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceController {

    private ServiceDao serviceDao;

    private ServiceService serviceService;

    private ResNatAreaServiceDao resNatAreaServiceDao;
    int codigos;

    @Autowired
    public void setServiceDao(ServiceDao serviceDao) {
        this.serviceDao=serviceDao;
    }

    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Autowired
    public void setResNatAreaServiceDao(ResNatAreaServiceDao resNatAreaServiceDao) {
        this.resNatAreaServiceDao = resNatAreaServiceDao;
    }

    @RequestMapping("/list")
    public String listServices(HttpSession session, Model model) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.MunicipalManager.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("service", serviceDao.getServices());

        return "service/list";
    }
    @RequestMapping(value="/add")
    public String addService(Model model) {
        if(!model.containsAttribute("service"))
            model.addAttribute("service", new Service());
        model.addAttribute("type_of_service", serviceService.getAllServiceTypes());
        return "service/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("service") final Service service, RedirectAttributes attributes,
                                   final BindingResult bindingResult) {

        codigos = (int)(Math.random()*100000);
        service.setCode( String.valueOf(codigos));
        ServiceValidator serviceValidator = new ServiceValidator();
        serviceValidator.validate(service, bindingResult);
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.service",bindingResult);
            attributes.addFlashAttribute("service",service);
            return "redirect:/service/add";
        }
        try {
            for(Service ser:serviceDao.getServices()){
                if(service.getType_of_service().equals(ser.getType_of_service()) && service.getDescription().equals(ser.getDescription())){
                    throw new ProyectoException(
                            "Ya existe el servicio "
                                    + service.getDescription() + " con el tipo de servicio "+service.getType_of_service(), "CPduplicada");
                }
            }
            serviceDao.addService(service);
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

    @RequestMapping(value={"/update/{code}","/update"}, method = RequestMethod.GET)
    public String editService(Model model, @PathVariable(required = false) String code) {
        if(!model.containsAttribute("service"))
            model.addAttribute("service", serviceDao.getService(code));
        model.addAttribute("type_of_service", serviceService.getAllServiceTypes());


        return "service/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("service") final Service service, RedirectAttributes attributes,
            final BindingResult bindingResult) {
        ServiceValidator serviceValidator = new ServiceValidator();
        serviceValidator.validate(service, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.service",bindingResult);
            attributes.addFlashAttribute("service",service);
            return "redirect:/service/update";}
        try {
            for(Service ser:serviceDao.getServices()){
                if(service.getType_of_service().equals(ser.getType_of_service()) && service.getDescription().equals(ser.getDescription())){
                    throw new ProyectoException(
                            "Ya existe el servicio "
                                    + service.getDescription() + " con el tipo de servicio "+service.getType_of_service(), "CPduplicada");
                }
            }
            serviceDao.updateService(service);
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{code}")
    public String processDelete(@PathVariable String code) {

        try{
            serviceDao.deleteService(code);
            return "redirect:../list";
        } catch (Exception e) {
            throw new ProyectoException(
                    "Lo sentimos pero este servicio está en uso. Comprueba que no hay áreas naturales usandolo.", "ErrorAccedintDades");

        }
    }


}

