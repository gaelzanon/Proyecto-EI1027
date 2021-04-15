package es.uji.ei1027.projecto1027.controller;


import es.uji.ei1027.projecto1027.Dao.CitizenDao;
import es.uji.ei1027.projecto1027.model.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/citizen")
public class CitizenController {

    private CitizenDao CitizenDao;

    @Autowired
    public void setCitizenDao(CitizenDao citizenDao) {
        this.CitizenDao=citizenDao;
    }

    @RequestMapping("/list")
    public String listCitizens(Model model) {
        model.addAttribute("citizen", CitizenDao.getCitizens());
        return "citizen/list";
    }
    @RequestMapping(value="/add")
    public String addCitizen(Model model) {
        model.addAttribute("citizen", new Citizen());
        return "citizen/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("citizen") Citizen citizen,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "citizen/add";
        CitizenDao.addCitizen(citizen);
        return "redirect:list";
    }
    @RequestMapping(value="/update/{nom}", method = RequestMethod.GET)
    public String editCitizen(Model model, @PathVariable String NIF) {
        model.addAttribute("citizen", CitizenDao.getCitizen(NIF));
        return "citizen/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("citizen") Citizen citizen,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "citizen/update";
        CitizenDao.updateCitizen(citizen);
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{nom}")
    public String processDelete(@PathVariable String NIF) {
        CitizenDao.deleteCitizen(NIF);
        return "redirect:../list";
    }






}

