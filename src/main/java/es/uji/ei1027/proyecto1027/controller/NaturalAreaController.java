package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;

import es.uji.ei1027.proyecto1027.model.NaturalArea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/naturalArea")
public class NaturalAreaController {

    private NaturalAreaDao NaturalAreaDao;

    @Autowired
    public void setnaturalAreaDao(NaturalAreaDao NaturalAreaDao) {
        this.NaturalAreaDao=NaturalAreaDao;
    }

    @RequestMapping("/list")
    public String listnaturalAreas(Model model) {
        model.addAttribute("naturalArea", NaturalAreaDao.getNaturalArea());
        return "naturalArea/list";
    }
    @RequestMapping(value="/add")
    public String addnaturalArea(Model model) {
        model.addAttribute("naturalArea", new NaturalArea());
        return "naturalArea/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("naturalArea") NaturalArea naturalArea,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "naturalArea/add";
        NaturalAreaDao.addNaturalArea(naturalArea);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{codeArea}", method = RequestMethod.GET)
    public String editnaturalArea(Model model, @PathVariable String codeArea) {
        model.addAttribute("naturalArea", NaturalAreaDao.getNaturalArea(codeArea));

        return "naturalArea/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("naturalArea") NaturalArea naturalArea,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "naturalArea/update";
        NaturalAreaDao.updateNaturalArea(naturalArea);
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{codeArea}")
    public String processDelete(@PathVariable String codeArea) {
        NaturalAreaDao.deleteNaturalArea(codeArea);
        return "redirect:../list";
    }






}