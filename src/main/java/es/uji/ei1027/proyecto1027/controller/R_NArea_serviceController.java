package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.Dao.R_NArea_serviceDao;
import es.uji.ei1027.proyecto1027.model.R_NArea_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/res_natArea_ser")
public class R_NArea_serviceController {

    private R_NArea_serviceDao res_natArea_serDao;

    @Autowired
    public void SetR_NArea_serviceDao(R_NArea_serviceDao res_natArea_serDao) {
        this.res_natArea_serDao = res_natArea_serDao;
    }

    @RequestMapping(value="/add")
    public String addR_NArea_service(Model model) {
        model.addAttribute("res_natArea_ser", new R_NArea_service());
        return "res_natArea_ser/add";
    }

    @RequestMapping(value="/delete/{areaCode}/{code]")
    public String processDeleteR_NArea_service(@PathVariable String areaCode, @PathVariable String code) {
        res_natArea_serDao.deleteR_NArea_service(areaCode, code);
        return "redirect:../../list";
    }
}
