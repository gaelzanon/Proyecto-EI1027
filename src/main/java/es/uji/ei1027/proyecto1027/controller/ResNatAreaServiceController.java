package es.uji.ei1027.proyecto1027.controller;


//import com.sun.org.apache.xpath.internal.operations.Mod;
import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.dao.ServiceDao;
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
import org.springframework.web.util.UriUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/resNatAreaSer")
public class ResNatAreaServiceController {

    private ResNatAreaServiceDao resNatAreaServiceDao;

    private ResNatAreaSerService resNatAreaSerService;

    private NaturalAreaDao naturalAreaDao;

    private ServiceDao serviceDao;

    @Autowired
    public void setResNatAreaSerService(ResNatAreaSerService resNatAreaSerService) {
        this.resNatAreaSerService = resNatAreaSerService;
    }

    @Autowired
    public void setResNatAreaServiceDao(ResNatAreaServiceDao ResNatAreaServiceDao) {
        this.resNatAreaServiceDao=ResNatAreaServiceDao;
    }

    @Autowired
    public void setResNatAreaNaturalAreaDao(NaturalAreaDao NaturalAreaDao) {
        this.naturalAreaDao=NaturalAreaDao;
    }

    @Autowired
    public void setServiceDao(ServiceDao ServiceDao) {
        this.serviceDao=ServiceDao;
    }

    @RequestMapping("/list")
    public String listResNatAreaServices(Model model) {
        model.addAttribute("resNatAreaSers", resNatAreaServiceDao.getR_NArea_services());

        return "resNatAreaSer/list";
    }
    @RequestMapping(value="/add")
    public String addResNatAreaService(Model model) {
        model.addAttribute("resNatAreaSer", new ResNatAreaService());
        model.addAttribute("code_area", naturalAreaDao.getNaturalAreaNames());
        model.addAttribute("code",serviceDao.getServiceNames());
        return "resNatAreaSer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("resNatAreaSer") ResNatAreaService resNatAreaService,
                                   BindingResult bindingResult) {
        resNatAreaService.setCode_area(naturalAreaDao.getNaturalAreaCode(resNatAreaService.getCode_area()));
        resNatAreaService.setCode(serviceDao.getServiceCode(resNatAreaService.getCode()));
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
        List<String> serDisp = resNatAreaSerService.getAllServices();
        List<ResNatAreaService> servAsig = resNatAreaSerService.getResNatAreaServiceByArea(code_area);
        for (ResNatAreaService servicio : servAsig) {
            String codSer = servicio.getCode();
            serDisp.remove(codSer);
        }
        model.addAttribute("services", serDisp);
        model.addAttribute("resNatAreaSersPA", servAsig);
        return "resNatAreaSer/porArea";
    }

    @RequestMapping(value="/porArea", method=RequestMethod.POST)
    public String processAddSubmitPerProva(
            @ModelAttribute("resNatAreaSer") ResNatAreaService resNatAreaService,
            BindingResult bindingResult) {
        ResNatAreaServiceValidator resNatAreaServiceValidator = new ResNatAreaServiceValidator();
        resNatAreaServiceValidator.validate(resNatAreaService, bindingResult);
        String nameUri="redirect:porArea/" + resNatAreaService.getCode_area();
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        if (bindingResult.hasErrors())
            return nameUri;
        try {
            resNatAreaServiceDao.addR_NArea_service(resNatAreaService);
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya está asignado el servicio "
                            + resNatAreaService.getCode() + " al area "
                            + resNatAreaService.getCode_area(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return nameUri;
    }

    @RequestMapping(value = "/delete/{code_area}/{code}")
    public String processDeleteResNatAreaService(@PathVariable String code_area,
                                                 @PathVariable String code) {
        resNatAreaServiceDao.deleteR_NArea_service(code_area, code);
        String nameUri="redirect:../../porArea/" + code_area;
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        return nameUri;
    }






}

