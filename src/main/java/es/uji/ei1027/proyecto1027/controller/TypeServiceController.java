package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.ServiceDao;
import es.uji.ei1027.proyecto1027.dao.TypeServiceDao;
import es.uji.ei1027.proyecto1027.model.Service;
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
@RequestMapping("/typeService")
public class TypeServiceController {

    private TypeServiceDao typeServiceDao;

    @Autowired
    public void setTypeServiceDao(TypeServiceDao typeServiceDao) {
        this.typeServiceDao=typeServiceDao;
    }

    @RequestMapping("/list")
    public String listTypeServices(Model model) {
        model.addAttribute("type_of_service", typeServiceDao.getTypeServices());
        System.out.println(typeServiceDao.getTypeServices());
        return "typeService/list";
    }
    @RequestMapping(value="/add")
    public String addTypeService(Model model) {
        model.addAttribute("type_of_service", new TypeService());
        return "service/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("type_of_service") TypeService typeService,
                                   BindingResult bindingResult) {
        TypeServiceValidator typeServiceValidator = new TypeServiceValidator();
        typeServiceValidator.validate(typeService, bindingResult);
        if (bindingResult.hasErrors())
            return "typeService/add";
        try {
            typeServiceDao.addTypeService(typeService);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el tipo de servicio "
                            + typeService.getType(), "CPduplicada");
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
        typeServiceDao.deleteTypeService(type);
        return "redirect:../list";
    }






}

