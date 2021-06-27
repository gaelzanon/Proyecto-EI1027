package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.MunicipalityDao;
import es.uji.ei1027.proyecto1027.dao.MunicipalityManagerDao;
import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.model.Municipality;
import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
import es.uji.ei1027.proyecto1027.model.NaturalArea;
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

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/municipality")
public class MunicipalityController {
    private NaturalAreaDao naturalAreaDao;
    private MunicipalityDao municipalityDao;
    private int codigos;

    @Autowired
    public void SetMunicipalityDao(MunicipalityDao municipalityDao) {
        this.municipalityDao = municipalityDao;
    }
    @Autowired
    public void setNaturalAreaDao(NaturalAreaDao naturalAreaDao) {
        this.naturalAreaDao=naturalAreaDao;
    }


    @RequestMapping("/list")
    public String listMunicipality(Model model) {
        model.addAttribute("municipality", municipalityDao.getMunicipality());
        return "municipality/list";
    }

    @RequestMapping(value="/add")
    public String addMunicipality(Model model) {
        model.addAttribute("municipality", new Municipality());
        return "municipality/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("municipality") Municipality municipality,
                                   BindingResult bindingResult) {

        codigos = (int)(Math.random()*100000);
        municipality.setCode( String.valueOf(codigos));
        MunicipalityValidator municipalityValidator = new MunicipalityValidator();
        municipalityValidator.validate(municipality, bindingResult);
        if (bindingResult.hasErrors())
            return "municipality/add";
        try {
            municipalityDao.addMunicipality(municipality);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya esta dado de alta el municipio"
                            + municipality.getCode() + " de codigo "
                            + municipality.getName(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }

    @RequestMapping(value="/update/{code}", method= RequestMethod.GET)
    public String editMunicipality(Model model, @PathVariable String code) {
        model.addAttribute("municipality", municipalityDao.getMunicipality(code));
        return "municipality/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("municipality") Municipality mun,
            BindingResult bindingResult) {
        MunicipalityValidator municipalityValidator = new MunicipalityValidator();
        municipalityValidator.validate(mun, bindingResult);
        if (bindingResult.hasErrors())
            return "municipality/update";
        try {
            municipalityDao.updateMunicipality(mun);
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }
    @RequestMapping(value={"/details/{code}"}, method = RequestMethod.GET)
    public String detailsnaturalArea(Model model, @PathVariable(required = false) String code) {
        if(!model.containsAttribute("municipality"))
            model.addAttribute("municipality", municipalityDao.getMunicipality(code));

        return "municipality/details";
    }

    @RequestMapping(value="/delete/{code}")
    public String processDeleteMunicipality(@PathVariable String code) {
        List<String> list = naturalAreaDao.getNaturalAreaMunicipios();
        if(list.contains(code)){
            throw new ProyectoException(
                    "Lo sentimos pero este municipio tiene reservas sin terminar", "ErrorAccedintDades");
        }
        municipalityDao.deleteMunicipality(code);
        return "redirect:../list";
    }

}