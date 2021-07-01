package es.uji.ei1027.proyecto1027.controller;


//import com.sun.org.apache.xpath.internal.operations.Mod;
import es.uji.ei1027.proyecto1027.dao.*;
import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.model.ResNatAreaServiceTem;
import es.uji.ei1027.proyecto1027.model.ServiceTem;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/resNatAreaSerTem")
public class ResNatAreaServiceTemController {

    private ResNatAreaServiceTemDao resNatAreaServiceTemDao;

    private ResNatAreaSerService resNatAreaSerService;

    private NaturalAreaDao naturalAreaDao;

    private ServiceTemDao serviceTemDao;

    private ServiceTem serviceTem;

    @Autowired
    public void setResNatAreaSerService(ResNatAreaSerService resNatAreaSerService) {
        this.resNatAreaSerService = resNatAreaSerService;
    }

    @Autowired
    public void setResNatAreaServiceTemDao(ResNatAreaServiceTemDao ResNatAreaServiceTemDao) {
        this.resNatAreaServiceTemDao=ResNatAreaServiceTemDao;
    }

    @Autowired
    public void setResNatAreaNaturalAreaDao(NaturalAreaDao NaturalAreaDao) {
        this.naturalAreaDao=NaturalAreaDao;
    }

    @Autowired
    public void setServiceTemDao(ServiceTemDao ServiceTemDao) {
        this.serviceTemDao=ServiceTemDao;
    }

    @RequestMapping("/list")
    public String listResNatAreaServicesTem(HttpSession session, Model model) {
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        model.addAttribute("resNatAreaSerTems", resNatAreaServiceTemDao.getR_NArea_servicesTem());
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        model.addAttribute("service",serviceTemDao.getServicesTem());

        return "resNatAreaSerTem/list";
    }
    @RequestMapping(value="/add")
    public String addResNatAreaServiceTem(Model model) {
        model.addAttribute("resNatAreaSerTem", new ResNatAreaServiceTem());
        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        model.addAttribute("service",serviceTemDao.getServicesTem());
        return "resNatAreaSerTem/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("resNatAreaSerTem") ResNatAreaServiceTem resNatAreaServiceTem, RedirectAttributes attributes,
                                   BindingResult bindingResult) {
        //resNatAreaService.setCode_area(naturalAreaDao.getNaturalAreaCode(resNatAreaService.getCode_area()));
        //resNatAreaService.setCode(serviceDao.getServiceCode(resNatAreaService.getCode()));
        //ResNatAreaServiceValidator resNatAreaServiceValidator = new ResNatAreaServiceValidator();
        //resNatAreaServiceValidator.validate(resNatAreaServiceTem, bindingResult);
        boolean puede = serviceTemDao.getServiceTemEnd(resNatAreaServiceTem.getCode(),resNatAreaServiceTem.getStartTime());

        if(puede==false){
            throw new ProyectoException(
                    "Lo sentimos pero ese Servicio Terminara antes de que lo puedas poner en Area Natural", "ErrorAccedintDades");
        }
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.resNatAreaService", bindingResult);
            attributes.addFlashAttribute("resNatAreaServiceTem", resNatAreaServiceTem);
            return "redirect:/resNatAreaSerTem/add";
        }
        try {
            resNatAreaServiceTemDao.addR_NArea_serviceTem(resNatAreaServiceTem);
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe dado de alta el servicio "
                            + resNatAreaServiceTem.getCode() + " en el area "
                            + resNatAreaServiceTem.getCode_area(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }

        return "redirect:list";
    }

    @RequestMapping("/porArea/{code_area}")
    public String listResNatAreaServiceTemPorArea(Model model, @PathVariable String code_area) {
        ResNatAreaServiceTem resNatAreaServiceTem = new ResNatAreaServiceTem();
        resNatAreaServiceTem.setCode_area(code_area);
        model.addAttribute("codarea", resNatAreaServiceTem);
        List<String> serDisp = resNatAreaSerService.getAllServices();
        List<ResNatAreaService> servAsig = resNatAreaSerService.getResNatAreaServiceByArea(code_area);
        model.addAttribute("services", serDisp);
        model.addAttribute("resNatAreaSersTemPA", servAsig);

        model.addAttribute("naturalArea",naturalAreaDao.getNaturalArea());
        model.addAttribute("serviceList",serviceTemDao.getServicesTem());
        return "resNatAreaSerTem/porArea";
    }

    @RequestMapping(value="/porArea", method=RequestMethod.POST)
    public String processAddSubmitPerProva(
            @ModelAttribute("resNatAreaSerTem") ResNatAreaServiceTem resNatAreaServiceTem,
            BindingResult bindingResult) {
        //ResNatAreaServiceValidator resNatAreaServiceValidator = new ResNatAreaServiceValidator();
        //resNatAreaServiceValidator.validate(resNatAreaServiceTem, bindingResult);
        String nameUri="redirect:porArea/" + resNatAreaServiceTem.getCode_area();
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        if (bindingResult.hasErrors())
            return nameUri;
        try {
            resNatAreaServiceTemDao.addR_NArea_serviceTem(resNatAreaServiceTem);
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya está asignado el servicio "
                            + resNatAreaServiceTem.getCode() + " al area "
                            + resNatAreaServiceTem.getCode_area(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return nameUri;
    }

    @RequestMapping(value = "/delete/{codearea}/{code}/{startTime}/{endtime}")
    public String processDeleteResNatAreaService(@PathVariable String codearea,
                                                 @PathVariable String code, @PathVariable String startTime, @PathVariable String endtime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");String date = "16/08/2016";//convert String to LocalDateLocalDate localDate = LocalDate.parse(date, formatter);
        resNatAreaServiceTemDao.deleteR_NArea_serviceTem(codearea, code, startTime, endtime);
        String nameUri="redirect:../../../../porArea/" + codearea;
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        return nameUri;
    }






}

