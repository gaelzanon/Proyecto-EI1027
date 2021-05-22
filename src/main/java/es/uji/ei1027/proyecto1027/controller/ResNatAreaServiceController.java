package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
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
@RequestMapping("/resNatAreaSer")
public class ResNatAreaServiceController {

    private ResNatAreaServiceDao ResNatAreaServiceDao;

    @Autowired
    public void setResNatAreaServiceDao(ResNatAreaServiceDao ResNatAreaServiceDao) {
        this.ResNatAreaServiceDao=ResNatAreaServiceDao;
    }

    @RequestMapping("/list")
    public String listResNatAreaServices(Model model) {
        model.addAttribute("resNatAreaSer", ResNatAreaServiceDao.getR_NArea_services());
        System.out.println(ResNatAreaServiceDao.getR_NArea_services());
        return "resNatAreaSer/list";
    }
    @RequestMapping(value="/add")
    public String addResNatAreaService(Model model) {
        model.addAttribute("resNatAreaSer", new ResNatAreaService());
        return "resNatAreaSer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("resNatAreaSer") ResNatAreaService resNatAreaService,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "resNatAreaSer/add";
        try {
            ResNatAreaServiceDao.addR_NArea_service(resNatAreaService);
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe dado de alta el servicio "
                            + resNatAreaService.getCode() + " en el area "
                            + resNatAreaService.getCode_area(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
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
    @RequestMapping(value="/delete/{code_area}")
    public String processDelete(@PathVariable String code_area, String code) {
        ResNatAreaServiceDao.deleteR_NArea_service(code_area, code);
        return "redirect:../list";
    }






}

