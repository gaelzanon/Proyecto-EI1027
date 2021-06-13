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
import org.springframework.web.util.UriUtils;

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
        String nameUri="redirect:../../../porArea/" + areaCode;
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        return nameUri;
    }

    @RequestMapping("/porArea/{code_area}")
    public String listZonePorArea(Model model, @PathVariable String code_area) {
        Zone zone = new Zone();
        zone.setAreaCode(code_area);
        model.addAttribute("codeArea", zone);
        List<Zone> zones = zoneDao.getZonesArea(code_area);
        model.addAttribute("zones", zones);
        return "zone/porArea";
    }

    @RequestMapping(value="/porArea", method = RequestMethod.POST)
    public String processAddSubmitPerArea(@ModelAttribute("zone") Zone zone, BindingResult bindingResult) {
        String nameUri="redirect:porArea/" + zone.getAreaCode();
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        if (bindingResult.hasErrors())
            return nameUri;
        try {
            zoneDao.addZone(zone);
        } catch (DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya está asignada la zona con columna "
                            + zone.getCol() + " y fila "
                            + zone.getRow() + "al area "
                            + zone.getAreaCode(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return nameUri;
    }

}