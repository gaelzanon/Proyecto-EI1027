package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.dao.TypeAreaDao;
import es.uji.ei1027.proyecto1027.dao.TypeServiceDao;
import es.uji.ei1027.proyecto1027.model.TypeNaturalArea;
import es.uji.ei1027.proyecto1027.model.TypeService;
import es.uji.ei1027.proyecto1027.model.UserDetails;
import es.uji.ei1027.proyecto1027.model.UserDetailsEnum;
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

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/typeArea")
public class TypeAreaController {

    private TypeAreaDao typeAreaDao;

    @Autowired
    public void setTypeAreaDao(TypeAreaDao typeAreaDao) {
        this.typeAreaDao=typeAreaDao;
    }

    @RequestMapping("/list")
    public String listTypeAreas(HttpSession session, Model model) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.EnvironmentalManager.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("type_of_area", typeAreaDao.getTypeAreas());
        return "typeArea/list";
    }
    @RequestMapping(value="/add")
    public String addTypeArea(Model model) {
        if(!model.containsAttribute("type_of_area"))
            model.addAttribute("type_of_area", new TypeNaturalArea());
        return "typeArea/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("type_of_area") final TypeNaturalArea typeNaturalArea,
                                   RedirectAttributes attributes, final BindingResult bindingResult) {
        TypeAreaValidator typeAreaValidator = new TypeAreaValidator();
        typeAreaValidator.validate(typeNaturalArea, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.type_of_area",bindingResult);
            attributes.addFlashAttribute("type_of_area",typeNaturalArea);
            return "redirect:/typeArea/add";}
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


    @RequestMapping(value="/delete/{type}")
    public String processDelete(@PathVariable String type) {

        try{
            typeAreaDao.deleteTypeArea(type);
            return "redirect:../list";
        } catch (Exception e){
            throw new ProyectoException(
                    "Lo sentimos pero existen areas con este tipo de area asignado.", "ErrorAccedintDades");
        }

    }






}

