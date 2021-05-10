package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ServiceDao;
import es.uji.ei1027.proyecto1027.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/Service")
public class ServiceController {

    private ServiceDao ServiceDao;

    @Autowired
    public void setServiceDao(ServiceDao ServiceDao) {
        this.ServiceDao=ServiceDao;
    }

    @RequestMapping("/list")
    public String listServices(Model model) {
        model.addAttribute("Service", ServiceDao.getServices());
        return "Service/list";
    }
    @RequestMapping(value="/add")
    public String addService(Model model) {
        model.addAttribute("Service", new Service());
        return "Service/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("Service") Service Service,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Service/add";
        ServiceDao.addService(Service);
        return "redirect:list";
    }
    @RequestMapping(value="/update/{nom}", method = RequestMethod.GET)
    public String editService(Model model, @PathVariable String NIF) {
        model.addAttribute("Service", ServiceDao.getService(NIF));
        return "Service/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("Service") Service Service,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Service/update";
        ServiceDao.updateService(Service);
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{nom}")
    public String processDelete(@PathVariable Service service) {
        ServiceDao.deleteService(service);
        return "redirect:../list";
    }






}

