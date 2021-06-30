package es.uji.ei1027.proyecto1027.controller;


//import com.sun.org.apache.xpath.internal.operations.Mod;
import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.dao.ServiceDao;
import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.model.UserDetails;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
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
    public String listResNatAreaServices(HttpSession session, Model model) {
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        model.addAttribute("resNatAreaSers", resNatAreaServiceDao.getR_NArea_services());
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        model.addAttribute("service",serviceDao.getServices());

        return "resNatAreaSer/list";
    }
    @RequestMapping(value="/add")
    public String addResNatAreaService(Model model) {
        model.addAttribute("resNatAreaSer", new ResNatAreaService());
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        model.addAttribute("service",serviceDao.getServices());
        return "resNatAreaSer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("resNatAreaSer") ResNatAreaService resNatAreaService, RedirectAttributes attributes,
                                   BindingResult bindingResult) {
        System.out.println(resNatAreaService);
        //resNatAreaService.setCode_area(naturalAreaDao.getNaturalAreaCode(resNatAreaService.getCode_area()));
        //resNatAreaService.setCode(serviceDao.getServiceCode(resNatAreaService.getCode()));
        ResNatAreaServiceValidator resNatAreaServiceValidator = new ResNatAreaServiceValidator();
        resNatAreaServiceValidator.validate(resNatAreaService, bindingResult);
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.resNatAreaService", bindingResult);
            attributes.addFlashAttribute("resNatAreaService", resNatAreaService);
            return "redirect:/resNatAreaSer/add";
        }
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
        model.addAttribute("services", serDisp);
        model.addAttribute("resNatAreaSersPA", servAsig);

        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        model.addAttribute("serviceList",serviceDao.getServices());
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

    @RequestMapping(value = "/delete/{codearea}/{code}")
    public String processDeleteResNatAreaService(@PathVariable String codearea,
                                                 @PathVariable String code) {


        resNatAreaServiceDao.deleteR_NArea_service(codearea, code);
        String nameUri="redirect:../../../../porArea/" + codearea;
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        return nameUri;
    }






}

