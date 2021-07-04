package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.UserDao;
import es.uji.ei1027.proyecto1027.model.UserDetails;
import es.uji.ei1027.proyecto1027.model.UserDetailsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class FaqController {

    @RequestMapping("/faq")
    public String faq(HttpSession session) {
        UserDetails user=(UserDetails) session.getAttribute("user");
        if ( user== null)
        {
            return "redirect:/";
        }
        return "faq/faq";
    }
}