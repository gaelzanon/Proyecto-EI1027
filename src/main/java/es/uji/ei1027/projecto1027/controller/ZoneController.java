package es.uji.ei1027.projecto1027.controller;

import es.uji.ei1027.projecto1027.Dao.ZoneDao;
import es.uji.ei1027.projecto1027.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public void SetZoneDao(ZoneDao zoneDao) {
        this.zoneDao = zoneDao;
    }

    @RequestMapping("/list")
    public String listZone(Model model) {
        model.addAttribute("zones", zoneDao.getZones());
        return "zone/list";
    }

    @RequestMapping(value="/add")
    public String addZone(Model model) {
        model.addAttribute("zone", new Zone());
        return "zone/add";
    }

    @RequestMapping(value="/update/{col}/{row}/{areaCode}", method=RequestMethod.GET)
    public String editZone(Model model, @PathVariable int col, @PathVariable int row, @PathVariable String areaCode) {
        model.addAttribute("zone", zoneDao.getZone(col, row, areaCode));
        return "zone/update";
    }

    @RequestMapping(value="/delete/{col}/{row}/{areaCode}")
    public String processDeleteZone(@PathVariable int col, @PathVariable int row, @PathVariable String areaCode) {
        zoneDao.deleteZone(col, row, areaCode);
        return "redirect:../../list";
    }

}