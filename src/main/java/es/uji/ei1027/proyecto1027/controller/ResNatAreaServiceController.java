package es.uji.ei1027.proyecto1027.controller;


//import com.sun.org.apache.xpath.internal.operations.Mod;
import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.dao.ServiceDao;
import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.model.UserDetails;
import es.uji.ei1027.proyecto1027.model.UserDetailsEnum;
import es.uji.ei1027.proyecto1027.services.ResNatAreaSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/resNatAreaSer")
public class ResNatAreaServiceController {

    private ResNatAreaServiceDao resNatAreaServiceDao;

    private ResNatAreaSerService resNatAreaSerService;

    private NaturalAreaDao naturalAreaDao;

    private ServiceDao serviceDao;

    private int codigos;

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
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.MunicipalManager.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("resNatAreaSers", resNatAreaServiceDao.getR_NArea_services());
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        model.addAttribute("service",serviceDao.getServices());

        return "redirect:/resNatAreaSer/porArea";
    }
    @RequestMapping(value="/add")
    public String addResNatAreaService(Model model) {
        if(!model.containsAttribute("resNatAreaSer"))
            model.addAttribute("resNatAreaSer", new ResNatAreaService());
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        model.addAttribute("service",serviceDao.getServices());
        return "resNatAreaSer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("resNatAreaSer") final ResNatAreaService resNatAreaService,
                                   RedirectAttributes attributes, final BindingResult bindingResult) {
        codigos = (int)(Math.random()*100000);
        resNatAreaService.setCode_relacion( String.valueOf(codigos));
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
            return "redirect:list";
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe dado de alta el servicio "
                            + serviceDao.getService(resNatAreaService.getCode()).getDescription() + " en el area "
                            + naturalAreaDao.getNaturalArea(resNatAreaService.getCode_area()).getName() +" cpn fecha de inicio "
                            + resNatAreaService.getStartTime()+ " y fecha de fin "+resNatAreaService.getEndTime(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }

    }

    @RequestMapping(value = {"/porArea/{code_area}","/porArea"},method = RequestMethod.GET)
    public String listResNatAreaServicePorArea(HttpSession session, Model model, @PathVariable(required = false) String code_area) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.MunicipalManager.toString()))
        {
            return "redirect:/";
        }
        if(!model.containsAttribute("resNatAreaSer")){
            ResNatAreaService resNatAreaService = new ResNatAreaService();
            resNatAreaService.setCode_area(code_area);
            model.addAttribute("resNatAreaSer", resNatAreaService);
        }

        if(!model.containsAttribute("resNatAreaSersPA")){
            List<ResNatAreaService> servAsig = resNatAreaSerService.getResNatAreaServiceByArea(code_area);
            model.addAttribute("resNatAreaSersPA", servAsig);
        }

        List<String> serDisp = resNatAreaSerService.getAllServices();
        model.addAttribute("services", serDisp);

        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        model.addAttribute("serviceList",serviceDao.getServices());
        return "resNatAreaSer/porArea";
    }

    @RequestMapping(value="/porArea", method=RequestMethod.POST)
    public String processAddSubmitPerProva(
            @ModelAttribute("resNatAreaSer") final ResNatAreaService resNatAreaService,
            RedirectAttributes attributes, final BindingResult bindingResult) {
        codigos = (int)(Math.random()*100000);
        resNatAreaService.setCode_relacion( String.valueOf(codigos));
        if(resNatAreaService.getEndTime()==null){resNatAreaService.setEndTime(LocalDate.of(1970,1,1));}
        ResNatAreaServiceValidator resNatAreaServiceValidator = new ResNatAreaServiceValidator();
        resNatAreaServiceValidator.validate(resNatAreaService, bindingResult);
        String nameUri="redirect:porArea/" + resNatAreaService.getCode_area();
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.resNatAreaSer",bindingResult);
            attributes.addFlashAttribute("resNatAreaSer",resNatAreaService);
            return nameUri; }
        try {
            resNatAreaServiceDao.addR_NArea_service(resNatAreaService);
            return nameUri;
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya está asignado el servicio "
                            + serviceDao.getService(resNatAreaService.getCode()).getDescription() + " en el area "
                            + naturalAreaDao.getNaturalArea(resNatAreaService.getCode_area()).getName() +" con fecha de inicio "
                            + resNatAreaService.getStartTime()+ " y fecha de fin "+resNatAreaService.getEndTime(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
    }

    @RequestMapping(value = "/delete/{Code_relacion}")
    public String processDeleteResNatAreaService(@PathVariable String Code_relacion) {

        try{
            String codeArea=resNatAreaServiceDao.getResNatAreaService(Code_relacion).getCode_area();
            resNatAreaServiceDao.deleteR_NArea_service(Code_relacion);
            return "redirect:/resNatAreaSer/porArea/"+codeArea;
        }catch (Exception e) {
            throw new ProyectoException(
                    "Error en la base de datos.", "ErrorAccedintDades");
        }
    }






}

