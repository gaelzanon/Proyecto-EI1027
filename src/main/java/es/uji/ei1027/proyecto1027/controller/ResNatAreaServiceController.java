package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ResNatAreaService")
public class ResNatAreaServiceController {

    private ResNatAreaServiceDao ResNatAreaServiceDao;

    @Autowired
    public void setResNatAreaServiceDao(ResNatAreaServiceDao ResNatAreaServiceDao) {
        this.ResNatAreaServiceDao=ResNatAreaServiceDao;
    }

    @RequestMapping("/list")
    public String listResNatAreaServices(Model model) {
        model.addAttribute("ResNatAreaService", ResNatAreaServiceDao.getR_NArea_services());
        return "ResNatAreaService/list";
    }
    @RequestMapping(value="/add")
    public String addResNatAreaService(Model model) {
        model.addAttribute("ResNatAreaService", new ResNatAreaService());
        return "ResNatAreaService/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("ResNatAreaService") ResNatAreaService ResNatAreaService,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "ResNatAreaService/add";
        ResNatAreaServiceDao.addR_NArea_service(ResNatAreaService);
        return "redirect:list";
    }
    /*
    @RequestMapping(value="/update/{nom}", method = RequestMethod.GET)
    public String editResNatAreaService(Model model, @PathVariable String NIF) {
        model.addAttribute("ResNatAreaService", ResNatAreaServiceDao.getR_NArea_services(NIF));
        return "ResNatAreaService/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("ResNatAreaService") ResNatAreaService ResNatAreaService,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "ResNatAreaService/update";
        ResNatAreaServiceDao.updateR_NArea_service(ResNatAreaService);
        return "redirect:list";
    }
    */
    @RequestMapping(value="/delete/{nom}")
    public String processDelete(@PathVariable String codeArea, String code) {
        ResNatAreaServiceDao.deleteR_NArea_service(codeArea, code);
        return "redirect:../list";
    }






}

