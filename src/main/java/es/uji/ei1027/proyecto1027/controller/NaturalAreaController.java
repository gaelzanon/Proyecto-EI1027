package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.MunicipalityDao;
import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;

import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.dao.TypeAreaDao;
import es.uji.ei1027.proyecto1027.model.*;

import es.uji.ei1027.proyecto1027.services.NaturalAreaService;
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
@RequestMapping("/naturalArea")
public class NaturalAreaController {

    private NaturalAreaDao NaturalAreaDao;
    private TypeAreaDao typeAreaDao;
    private MunicipalityDao municipalityDao;
    private NaturalAreaService naturalAreaService;
    private ResNatAreaServiceDao resNatAreaServiceDao;
    private static double codigos;
    @Autowired
    public void setnaturalAreaDao(NaturalAreaDao NaturalAreaDao) {
        this.NaturalAreaDao=NaturalAreaDao;
    }
    @Autowired
    public void setTypeAreaDao(TypeAreaDao typeAreaDao) {
        this.typeAreaDao=typeAreaDao;
    }
    @Autowired
    public void setmunicipalityDao(MunicipalityDao municipalityDao) {
        this.municipalityDao=municipalityDao;
    }
    @Autowired
    public void setNaturalAreaService(NaturalAreaService naturalAreaService) {
        this.naturalAreaService=naturalAreaService;
    }
    @Autowired
    public void setresNatAreaServiceDao(ResNatAreaServiceDao resNatAreaServiceDao) {
        this.resNatAreaServiceDao=resNatAreaServiceDao;
    }

    @RequestMapping("/list")
    public String listnaturalAreas(HttpSession session, Model model) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.MunicipalManager.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("naturalArea", NaturalAreaDao.getNaturalArea());
        model.addAttribute("municipality",municipalityDao.getMunicipality());
        return "naturalArea/list";
    }

    @RequestMapping("/listCliente")
    public String listClienteNaturalAreas(HttpSession session, Model model) {
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        model.addAttribute("naturalArea", NaturalAreaDao.getNaturalArea());
        model.addAttribute("municipality",municipalityDao.getMunicipality());
        return "naturalArea/listCliente";
    }

    @RequestMapping(value="/add")
    public String addnaturalArea(HttpSession session,Model model) {
        if (session.getAttribute("user") == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        if(!model.containsAttribute("naturalArea"))
            model.addAttribute("naturalArea", new NaturalArea());
        model.addAttribute("types_of_area", typeAreaDao.getTypeAreas());
        model.addAttribute("munCode",municipalityDao.getMunicipalityNames() );
        return "naturalArea/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit( @ModelAttribute("naturalArea") final NaturalArea naturalArea, RedirectAttributes attributes,
                                   final BindingResult bindingResult) {

        codigos = (int)(Math.random()*100000);
        naturalArea.setCodeArea( String.valueOf(codigos));
        naturalArea.setMunCode(municipalityDao.getMunicipalityCode(naturalArea.getMunCode()));
        NaturalAreaValidator naturalAreaValidator = new NaturalAreaValidator();
        naturalAreaValidator.validate(naturalArea, bindingResult);

        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.naturalArea",bindingResult);
            attributes.addFlashAttribute("naturalArea",naturalArea);
            return "redirect:/naturalArea/add";}
        try {
            NaturalAreaDao.addNaturalArea(naturalArea);
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el area "
                            + naturalArea.getName() + " en el municipio "
                            + naturalArea.getMunCode(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }

    @RequestMapping(value={"/update/{codeArea}","/update"}, method = RequestMethod.GET)
    public String editnaturalArea(Model model, @PathVariable(required = false) String codeArea) {
        if(!model.containsAttribute("naturalArea"))
            model.addAttribute("naturalArea", NaturalAreaDao.getNaturalArea(codeArea));
        List<String> stateList = Arrays.asList("Abierta", "Cerrada","Restringida");
        model.addAttribute("stateList", stateList);
        model.addAttribute("types_of_area", typeAreaDao.getTypeAreas());
        model.addAttribute("munCode",municipalityDao.getMunicipalityNames() );

        return "naturalArea/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("naturalArea") final NaturalArea naturalArea, RedirectAttributes attributes,
            final BindingResult bindingResult) {
        naturalArea.setMunCode(municipalityDao.getMunicipalityCode(naturalArea.getMunCode()));
        NaturalAreaValidator naturalAreaValidator = new NaturalAreaValidator();
        naturalAreaValidator.validate(naturalArea, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.naturalArea",bindingResult);
            attributes.addFlashAttribute("naturalArea",naturalArea);
            return "redirect:/naturalArea/update";}
        try {
            NaturalAreaDao.updateNaturalArea(naturalArea);
        } catch (DataAccessException e) {
            throw new ProyectoException(
                "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }

    @RequestMapping(value={"/details/{codeArea}","/details"}, method = RequestMethod.GET)
    public String detailsnaturalArea(HttpSession session,Model model, @PathVariable(required = false) String codeArea) {
        UserDetails userDetails=(UserDetails) session.getAttribute("user");
        if (userDetails == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        } else{
            model.addAttribute("user_type",userDetails.getUserType());
        }
        List<Zone> zonas = naturalAreaService.getAllNatAreaZones(codeArea);
        model.addAttribute("zones", zonas);
        if(!model.containsAttribute("naturalArea"))
            model.addAttribute("naturalArea", NaturalAreaDao.getNaturalArea(codeArea));
        List<String> stateList = Arrays.asList("Abierta", "Cerrada","Restringida");
        model.addAttribute("stateList", stateList);
        model.addAttribute("types_of_area", typeAreaDao.getTypeAreas());
        return "naturalArea/details";
    }

    @RequestMapping(value={"/detailsCitizen/{codeArea}","/details"}, method = RequestMethod.GET)
    public String detailsCitizennaturalArea(HttpSession session,Model model, @PathVariable(required = false) String codeArea) {
        UserDetails userDetails=(UserDetails) session.getAttribute("user");
        if (userDetails == null)
        {
            model.addAttribute("user", new UserDetails());
            return "login";
        } else{
            model.addAttribute("user_type",userDetails.getUserType());
        }
        List<Zone> zonas = naturalAreaService.getAllNatAreaZones(codeArea);
        model.addAttribute("zones", zonas);
        if(!model.containsAttribute("naturalArea"))
            model.addAttribute("naturalArea", NaturalAreaDao.getNaturalArea(codeArea));
        List<String> stateList = Arrays.asList("Abierta", "Cerrada","Restringida");
        model.addAttribute("stateList", stateList);
        model.addAttribute("types_of_area", typeAreaDao.getTypeAreas());
        return "naturalArea/detailsCitizen";
    }

    @RequestMapping(value="/delete/{codeArea}")
    public String processDelete(@PathVariable String codeArea) {
        List<String> list = resNatAreaServiceDao.getCodesArea();
        if(list.contains(codeArea)){
            throw new ProyectoException(
                    "Lo sentimos pero este area aun esta vinculado a servicios disponibles", "ErrorAccedintDades");
        }
        NaturalAreaDao.deleteNaturalArea(codeArea);
        return "redirect:../list";
    }

}