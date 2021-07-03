package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.dao.ZoneDao;
import es.uji.ei1027.proyecto1027.model.Citizen;
import es.uji.ei1027.proyecto1027.model.UserDetails;
import es.uji.ei1027.proyecto1027.model.UserDetailsEnum;
import es.uji.ei1027.proyecto1027.model.Zone;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/zone")
public class ZoneController {

    private ZoneDao zoneDao;
    private NaturalAreaDao naturalAreaDao;

    @Autowired
    public void SetZoneDao(ZoneDao zoneDao) {
        this.zoneDao = zoneDao;
    }

    @Autowired
    public void setNaturalAreaDao(NaturalAreaDao naturalAreaDao) {
        this.naturalAreaDao = naturalAreaDao;
    }

    @RequestMapping("/list")
    public String listZone(HttpSession session, Model model) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.Admin.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("zone", zoneDao.getZones());
        return "zone/list";
    }

    @RequestMapping(value="/add")
    public String addZone(Model model) {
        if(!model.containsAttribute("zone"))
            model.addAttribute("zone", new Zone());
        return "zone/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("zone") final Zone zone,
                                   RedirectAttributes attributes, final BindingResult bindingResult) {
        int codigos = (int)(Math.random()*100000);
        zone.setCode( String.valueOf(codigos));
        ZoneValidator zoneValidator = new ZoneValidator();
        zoneValidator.validate(zone, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.zone",bindingResult);
            attributes.addFlashAttribute("zone",zone);
            return "redirect:/zone/add"; }
        try {
            zoneDao.addZone(zone);
        } catch (
    DuplicateKeyException e) {
        throw new ProyectoException(
                "Ya está creada la zona ("
                        + zone.getCol() + ", " + zone.getRow() + ") en el area "
                        + zone.getAreaCode(), "CPduplicada");
    } catch (
    DataAccessException e) {
        throw new ProyectoException(
                "Error en el acceso a la base de datos", "ErrorAccedintDades");
    }
        return "redirect:list";
    }

    @RequestMapping(value="/update/{code}", method=RequestMethod.GET)
    public String editZone(Model model, @PathVariable String code) {
        model.addAttribute("zone", zoneDao.getZone(code));
        return "zone/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("zone") Zone zone,
            BindingResult bindingResult) {
        ZoneValidator zoneValidator = new ZoneValidator();
        zoneValidator.validate(zone, bindingResult);
        if (bindingResult.hasErrors())
            return "zone/update";
        zoneDao.updateZone(zone);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{code}")
    public String processDeleteZone(@PathVariable String code) {
        String areaCode = zoneDao.getZone(code).getAreaCode();
        try{
            zoneDao.deleteZone(code);
            String nameUri="redirect:../porArea/" + areaCode;
            System.out.println(nameUri);
            nameUri = UriUtils.encodePath(nameUri, "UTF-8");
            return nameUri;
        } catch (Exception e){
            throw new ProyectoException(
                    "Lo sentimos pero esta zona está en uso. Comprueba que no tiene reservas ni controladores asignados.", "ErrorAccedintDades");
        }
    }

    @RequestMapping(value={"/porArea/{code_area}","/porArea"}, method = RequestMethod.GET)
    public String listZonePorArea(HttpSession session,Model model, @PathVariable(required = false) String code_area) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.MunicipalManager.toString()))
        {
            return "redirect:/";
        }
        Zone zone = new Zone();
        zone.setAreaCode(code_area);
        if(!model.containsAttribute("zone"))
            model.addAttribute("zone", zone);
        List<Zone> zones = zoneDao.getZonesArea(code_area);
        if(!model.containsAttribute("zones"))
            model.addAttribute("zones", zones);
        if(!model.containsAttribute("naturalArea"))
            model.addAttribute("naturalArea", naturalAreaDao.getNaturalArea(code_area).getName());
        return "zone/porArea";
    }

    @RequestMapping(value="/porArea", method = RequestMethod.POST)
    public String processAddSubmitPerArea(@ModelAttribute("zone") final Zone zone, @ModelAttribute("zones") final ArrayList<Zone> zones, @ModelAttribute("naturalArea") final String naturalArea, RedirectAttributes attributes,
                                          final BindingResult bindingResult) {
        String nameUri="redirect:porArea/" + zone.getAreaCode();
        int codigos = (int)(Math.random()*100000);
        zone.setCode( String.valueOf(codigos));
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        ZoneValidator zoneValidator = new ZoneValidator();
        zoneValidator.validate(zone, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.zone",bindingResult);
            attributes.addFlashAttribute("zone",zone);
            attributes.addFlashAttribute("zones",zones);
            attributes.addFlashAttribute("naturalArea",naturalArea);
            return "redirect:/zone/porArea"; }
        try {
            zoneDao.addZone(zone);
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya está asignada la zona con columna "
                            + zone.getCol() + " y fila "
                            + zone.getRow() + " al area "
                            + naturalAreaDao.getNaturalArea(zone.getAreaCode()).getName(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return nameUri;
    }

}