package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.TypeAreaDao;
import es.uji.ei1027.proyecto1027.dao.TypeServiceDao;
import es.uji.ei1027.proyecto1027.model.TypeNaturalArea;
import es.uji.ei1027.proyecto1027.model.TypeService;
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
@RequestMapping("/typeArea")
public class TypeAreaController {

    private TypeAreaDao typeAreaDao;

    @Autowired
    public void setTypeAreaDao(TypeAreaDao typeAreaDao) {
        this.typeAreaDao=typeAreaDao;
    }

    @RequestMapping("/list")
    public String listTypeAreas(Model model) {
        model.addAttribute("type_of_area", typeAreaDao.getTypeAreas());
        System.out.println(typeAreaDao.getTypeAreas());
        return "typeArea/list";
    }
    @RequestMapping(value="/add")
    public String addTypeArea(Model model) {
        model.addAttribute("type_of_area", new TypeNaturalArea());
        return "typeArea/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("type_of_area") TypeNaturalArea typeNaturalArea,
                                   BindingResult bindingResult) {
        TypeAreaValidator typeAreaValidator = new TypeAreaValidator();
        typeAreaValidator.validate(typeNaturalArea, bindingResult);
        if (bindingResult.hasErrors())
            return "typeArea/add";
        try {
            typeAreaDao.addTypeArea(typeNaturalArea);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el tipo de area "
                            + typeNaturalArea.getType(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }

    /* No se actualiza, solo hay clave primaria
    @RequestMapping(value="/update/{code}", method = RequestMethod.GET)
    public String editService(Model model, @PathVariable String code) {
        model.addAttribute("service", ServiceDao.getService(code));

        return "typeService/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("service") Service service,
            BindingResult bindingResult) {
        ServiceValidator serviceValidator = new ServiceValidator();
        serviceValidator.validate(service, bindingResult);
        if (bindingResult.hasErrors())
            return "typeService/update";
        try {
            ServiceDao.updateService(service);
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }*/

    @RequestMapping(value="/delete/{type}")
    public String processDelete(@PathVariable String type) {
        typeAreaDao.deleteTypeArea(type);
        return "redirect:../list";
    }






}

