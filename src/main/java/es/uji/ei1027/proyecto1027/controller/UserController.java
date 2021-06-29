package es.uji.ei1027.proyecto1027.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.proyecto1027.dao.UserDao;
import es.uji.ei1027.proyecto1027.model.UserDetails;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserDao userDao;
    private String nextUrl;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/list")
    public String listaUsuarios(HttpSession session, Model model) {
        if (session.getAttribute("user") == null)
        {
            nextUrl = "user/list";
            model.addAttribute("user", new UserDetails());
            session.setAttribute("nextUrl", nextUrl);
            return "login";
        }
        model.addAttribute("users", userDao.listAllUsers());
        return "user/list";
    }

    @RequestMapping("/self")
    public String gestionPerfilPropio(HttpSession session){
        UserDetails userDetails=(UserDetails) session.getAttribute("user");
        if (userDetails == null){
            return "login";
        }
        String redirect="";
        switch (userDetails.getUserType()){
            case  "Citizen":
                redirect="citizen";
                break;
            case  "Controller":
                redirect="controller";
                break;
            case  "MunicipalManager":
                redirect="municipalityManager";
                break;
            default:
                return "redirect:/mainMenu";
        }
        return "redirect:/"+redirect+"/updatePerfil/"+userDetails.getNIF();
    }
}
