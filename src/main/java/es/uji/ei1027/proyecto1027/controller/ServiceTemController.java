package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.dao.ServiceDao;
import es.uji.ei1027.proyecto1027.dao.ServiceTemDao;
import es.uji.ei1027.proyecto1027.dao.ServiceTemDao;
import es.uji.ei1027.proyecto1027.dao.TypeServiceDao;
import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.model.Service;
import es.uji.ei1027.proyecto1027.model.ServiceTem;
import es.uji.ei1027.proyecto1027.model.UserDetails;
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
@RequestMapping("/servicetem")
public class ServiceTemController {

    private ServiceTemDao ServiceTemDao;

    private ServiceService serviceService;

    private ResNatAreaServiceDao resNatAreaServiceDao;
    int codigos;

    @Autowired
    public void setServiceTemDao(ServiceTemDao ServiceTemDao) {
        this.ServiceTemDao=ServiceTemDao;
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
    public String listServicesTem(HttpSession session, Model model) {
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        model.addAttribute("servicetem", ServiceTemDao.getServicesTem());

        return "servicetem/list";
    }
    @RequestMapping(value="/add")
    public String addServiceTem(Model model) {
        model.addAttribute("servicetem", new ServiceTem());
        model.addAttribute("type_of_service", serviceService.getAllServiceTypes());
        return "servicetem/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("servicetem") final ServiceTem serviceTem, RedirectAttributes attributes,
                                   final BindingResult bindingResult) {
        System.out.println(serviceTem);
        codigos = (int)(Math.random()*100000);
        serviceTem.setCode( String.valueOf(codigos));
        ServiceTemValidator serviceTemValidator = new ServiceTemValidator();
        serviceTemValidator.validate(serviceTem, bindingResult);
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.servicetem",bindingResult);
            attributes.addFlashAttribute("servicetem",serviceTem);
            return "redirect:/servicetem/add";
        }
        try {
            ServiceTemDao.addServiceTem(serviceTem);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el servicio "
                            + serviceTem.getCode(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }

        return "redirect:list";
    }

    @RequestMapping(value={"/update/{code}","/update"}, method = RequestMethod.GET)
    public String editServiceTem(Model model, @PathVariable(required = false) String code) {
        if(!model.containsAttribute("servicetem"))
            model.addAttribute("servicetem", ServiceTemDao.getServiceTem(code));
        model.addAttribute("type_of_service", serviceService.getAllServiceTypes());


        return "servicetem/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("servicetem") final ServiceTem serviceTem, RedirectAttributes attributes,
            final BindingResult bindingResult) {
        ServiceTemValidator serviceTemValidator = new ServiceTemValidator();
        serviceTemValidator.validate(serviceTem, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.servicetem",bindingResult);
            attributes.addFlashAttribute("servicetem",serviceTem);
            return "redirect:/servicetem/update";}
        try {
            ServiceTemDao.updateServiceTem(serviceTem);
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{code}")
    public String processDelete(@PathVariable String code) {
        List<String> list = resNatAreaServiceDao.getCodes();
        if(list.contains(code)){
            throw new ProyectoException(
                    "Lo sentimos pero este servicio temporal aun esta vinculado a una Area Natural", "ErrorAccedintDades");
        }
        ServiceTemDao.deleteServiceTem(code);
        return "redirect:../list";
    }






}