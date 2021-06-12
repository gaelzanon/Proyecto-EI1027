package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.dao.ZoneDao;
import es.uji.ei1027.proyecto1027.model.Citizen;
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

@Controller
@RequestMapping("/zone")
public class ZoneController {

    private ZoneDao zoneDao;
    private NaturalAreaDao naturalAreaDao;

    @Autowired
    public void SetZoneDao(ZoneDao zoneDao) {
        this.zoneDao = zoneDao;
    }

    @RequestMapping("/list")
    public String listZone(Model model) {
        model.addAttribute("zone", zoneDao.getZones());
        return "zone/list";
    }

    @RequestMapping(value="/add")
    public String addZone(Model model) {
        model.addAttribute("zone", new Zone());
        return "zone/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("zone") Zone zone,
                                   BindingResult bindingResult) {
        ZoneValidator zoneValidator = new ZoneValidator();
        zoneValidator.validate(zone, bindingResult);
        if (bindingResult.hasErrors())
            return "zone/add";
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

    @RequestMapping(value="/update/{col}/{row}/{areaCode}", method=RequestMethod.GET)
    public String editZone(Model model, @PathVariable int col, @PathVariable int row, @PathVariable String areaCode) {
        model.addAttribute("zone", zoneDao.getZone(col, row, areaCode));
        return "zone/update";
    }
    @RequestMapping(value="/porArea/{codeArea}")
    public String zonePorArea(Model model, @PathVariable String codeArea){
        model.addAttribute("codarea", naturalAreaDao.getNaturalArea(codeArea));
        return "zone/porArea";
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

    @RequestMapping(value="/delete/{col}/{row}/{areaCode}")
    public String processDeleteZone(@PathVariable int col, @PathVariable int row, @PathVariable String areaCode) {
        zoneDao.deleteZone(col, row, areaCode);
        return "redirect:../../../list";
    }

}