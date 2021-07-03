package es.uji.ei1027.proyecto1027.controller;


import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.dao.SolicitudDao;
import es.uji.ei1027.proyecto1027.dao.TypeAreaDao;
import es.uji.ei1027.proyecto1027.dao.TypeServiceDao;
import es.uji.ei1027.proyecto1027.model.*;
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
@RequestMapping("/solicitud")
public class SolicitudController {

    private SolicitudDao solicitudDao;

    private int codigos;

    @Autowired
    public void setSolicitudDao(SolicitudDao solicitudDao) {
        this.solicitudDao=solicitudDao;
    }

    @RequestMapping("/list")
    public String listtSolicituds(HttpSession session, Model model) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null || !user.getUserType().equals(UserDetailsEnum.EnvironmentalManager.toString()))
        {
            return "redirect:/";
        }
        model.addAttribute("solicitud", solicitudDao.getSolicituds());
        return "solicitud/list";
    }
    @RequestMapping(value="/add")
    public String addSolicitud(Model model) {
        if(!model.containsAttribute("solicitud"))
            model.addAttribute("solicitud", new Solicitud());
        return "solicitud/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("solicitud") final Solicitud solicitud,
                                   RedirectAttributes attributes, final BindingResult bindingResult) {
        codigos = (int)(Math.random()*100000);
        solicitud.setCode( String.valueOf(codigos));
        SolicitudValidator solicitudValidator = new SolicitudValidator();
        solicitudValidator.validate(solicitud, bindingResult);
        if (bindingResult.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.solicitud",bindingResult);
            attributes.addFlashAttribute("solicitud",solicitud);
            return "redirect:/solicitud/add";}
        try {
            solicitudDao.addSolicitud(solicitud);
        } catch (
                DuplicateKeyException e) {
            throw new ProyectoException(
                    "Ya existe el tipo de area "
                            + solicitud.getCode(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ProyectoException(
                    "Error en el acceso a la base de datos", "ErrorAccedintDades");
        }
        return "redirect:list";
    }


    @RequestMapping(value="/delete/{code}")
    public String processDelete(@PathVariable String code) {

        try{
            solicitudDao.deleteSolicitud(code);
            return "redirect:../list";
        } catch (Exception e){
            throw new ProyectoException(
                    "Lo sentimos pero no se puede borrar esa solicitud.", "ErrorAccedintDades");
        }

    }






}


