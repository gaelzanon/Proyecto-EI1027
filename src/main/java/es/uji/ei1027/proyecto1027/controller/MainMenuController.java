package es.uji.ei1027.proyecto1027.controller;

import es.uji.ei1027.proyecto1027.dao.UserDao;
import es.uji.ei1027.proyecto1027.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainMenuController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/mainMenu")
    public String checkMenu(HttpSession session){
        UserDetails user=(UserDetails) session.getAttribute("user");
        String redirect="";
        switch (user.getUserType()){
            case  "Citizen":
                redirect="mainMenuCitizen";
                break;
            case  "Controller":
                redirect="mainMenuController";
                break;
            case  "MunicipalManager":
                redirect="mainMenuMunicipal";
                break;
            case  "EnvironmentalManager":
                redirect="mainMenuEnvironmental";
                break;
            case "Admin":
                return "redirect:/";
        }
        return "mainMenu/"+redirect;
    }
}
