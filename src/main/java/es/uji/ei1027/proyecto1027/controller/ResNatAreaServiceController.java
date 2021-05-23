package es.uji.ei1027.proyecto1027.controller;


//import com.sun.org.apache.xpath.internal.operations.Mod;
import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.services.ResNatAreaSerService;
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

    private ResNatAreaServiceDao resNatAreaServiceDao;

    private ResNatAreaSerService resNatAreaSerService;

    @Autowired
    public void setResNatAreaSerService(ResNatAreaSerService resNatAreaSerService) {
        this.resNatAreaSerService = resNatAreaSerService;
    }

    @Autowired
    public void setResNatAreaServiceDao(ResNatAreaServiceDao ResNatAreaServiceDao) {
        this.resNatAreaServiceDao=ResNatAreaServiceDao;
    }

    @RequestMapping("/list")
    public String listResNatAreaServices(Model model) {
        model.addAttribute("resNatAreaSers", resNatAreaServiceDao.getR_NArea_services());
        System.out.println(resNatAreaServiceDao.getR_NArea_services());
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
        ResNatAreaServiceValidator resNatAreaServiceValidator = new ResNatAreaServiceValidator();
        resNatAreaServiceValidator.validate(resNatAreaService, bindingResult);
        if (bindingResult.hasErrors())
            return "resNatAreaSer/add";
        try {
            resNatAreaServiceDao.addR_NArea_service(resNatAreaService);
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

    @RequestMapping("/porArea/{code_area}")
    public String listResNatAreaServicePorArea(Model model, @PathVariable String code_area) {
        ResNatAreaService resNatAreaService = new ResNatAreaService();
        resNatAreaService.setCode_area(code_area);
        model.addAttribute("codarea", resNatAreaService);
        model.addAttribute("services", resNatAreaSerService.getAllServices());
        model.addAttribute("resNatAreaSersPA", resNatAreaSerService.getResNatAreaServiceByArea(code_area));
        return "resNatAreaSer/porArea";
    }

    @RequestMapping(value = "/delete/{code_area}/{code}")
    public String processDeleteResNatAreaService(@PathVariable String code_area,
                                                 @PathVariable String code) {
        resNatAreaServiceDao.deleteR_NArea_service(code_area, code);
        return "redirect:../../list";
    }






}

