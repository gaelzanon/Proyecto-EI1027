package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.Dao.MunicipalityManagerDao;
import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/municipalityManager")
public class MunicipalityManagerController {

    private MunicipalityManagerDao managerDao;

    @Autowired
    public void SetMunicipalityManagerDao(MunicipalityManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @RequestMapping("/list")
    public String listMunicipalityManager(Model model) {
        model.addAttribute("municipality_managers", managerDao.getMunicipalityManagers());
        return "municipality_manager/list";
    }

    @RequestMapping(value="/add")
    public String addZone(Model model) {
        model.addAttribute("municipality_manager", new MunicipalityManager());
        return "municipality_manager/add";
    }

    @RequestMapping(value="/update/{NIF}", method= RequestMethod.GET)
    public String editZone(Model model, @PathVariable String NIF) {
        model.addAttribute("municipality_manager", managerDao.getMunicipalityManager(NIF));
        return "municipality_manager/update";
    }

    @RequestMapping(value="/delete/{NIF}")
    public String processDeleteZone(@PathVariable String NIF) {
        managerDao.deleteMunicipalityManager(NIF);
        return "redirect:../../list";
    }

}