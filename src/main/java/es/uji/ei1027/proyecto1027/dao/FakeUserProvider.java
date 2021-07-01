package es.uji.ei1027.proyecto1027.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uji.ei1027.proyecto1027.model.*;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class FakeUserProvider implements UserDao {
    final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();

    private CitizenDao citizenDao;
    private ControllerDao controllerDao;
    private MunicipalityManagerDao municipalityManagerDao;
    private EnvironmentalManagerDao environmentalManagerDao;

    @Autowired
    public void setCitizenDao(CitizenDao citizenDao) {
        this.citizenDao=citizenDao;
    }
    @Autowired
    public void setControllerDao(ControllerDao controllerDao) {
        this.controllerDao=controllerDao;
    }
    @Autowired
    public void setMunicipalityManagerDao(MunicipalityManagerDao municipalityManagerDao) {
        this.municipalityManagerDao=municipalityManagerDao;
    }
    @Autowired
    public void setEnvironmentalManagerDao(EnvironmentalManagerDao environmentalManagerDao) {
        this.environmentalManagerDao=environmentalManagerDao;
    }

    public FakeUserProvider() {}

    @PostConstruct
    private void loadUsers(){
        knownUsers.clear();

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        List<Citizen> citizenList= citizenDao.getCitizens();
        List<Controller> controllerList= controllerDao.getControllers();
        List<MunicipalityManager> municipalityManagerList= municipalityManagerDao.getMunicipalityManagers();
        List<EnvironmentalManager> environmentalManagerList= environmentalManagerDao.getEnvironmentalManagers();

        for(Citizen citizen:citizenList){
            UserDetails userClient = new UserDetails();
            userClient.setUsername(citizen.getName());
            userClient.setPassword(passwordEncryptor.encryptPassword(citizen.getPassword()));
            userClient.setNIF(citizen.getNIF());
            userClient.setUserType(UserDetailsEnum.Citizen.toString());
            knownUsers.put(userClient.getNIF(), userClient);
        }

        for(Controller controller:controllerList){
            UserDetails userClient = new UserDetails();
            userClient.setUsername(controller.getName());
            userClient.setPassword(passwordEncryptor.encryptPassword(controller.getPassword()));
            userClient.setNIF(controller.getNIF());
            userClient.setUserType(UserDetailsEnum.Controller.toString());
            knownUsers.put(userClient.getNIF(), userClient);
        }

        for(MunicipalityManager municipalityManager:municipalityManagerList){
            UserDetails userClient = new UserDetails();
            userClient.setUsername(municipalityManager.getName());
            userClient.setPassword(passwordEncryptor.encryptPassword(municipalityManager.getPassword()));
            userClient.setNIF(municipalityManager.getNIF());
            userClient.setUserType(UserDetailsEnum.MunicipalManager.toString());
            knownUsers.put(userClient.getNIF(), userClient);
        }


        for(EnvironmentalManager environmentalManager:environmentalManagerList){
            UserDetails userClient = new UserDetails();
            userClient.setUsername(environmentalManager.getName());
            userClient.setPassword(passwordEncryptor.encryptPassword(environmentalManager.getPassword()));
            userClient.setNIF(environmentalManager.getNIF());
            userClient.setUserType(UserDetailsEnum.EnvironmentalManager.toString());
            knownUsers.put(userClient.getNIF(), userClient);
        }


        UserDetails userClient = new UserDetails();
        userClient.setUsername("citizen");
        userClient.setPassword(passwordEncryptor.encryptPassword("citizen"));
        userClient.setUserType(UserDetailsEnum.Citizen.toString());
        userClient.setNIF("00000000A");
        knownUsers.put("citizen", userClient);

        UserDetails userController = new UserDetails();
        userController.setUsername("controller");
        userController.setPassword(passwordEncryptor.encryptPassword("controller"));
        userController.setUserType(UserDetailsEnum.Controller.toString());
        userController.setNIF("11111111A");
        knownUsers.put("controller", userController);

        UserDetails userMunicipal = new UserDetails();
        userMunicipal.setUsername("municipal");
        userMunicipal.setPassword(passwordEncryptor.encryptPassword("municipal"));
        userMunicipal.setUserType(UserDetailsEnum.MunicipalManager.toString());
        userMunicipal.setNIF("22222222A");
        knownUsers.put("municipal", userMunicipal);

        UserDetails userEnvironmental = new UserDetails();
        userEnvironmental.setUsername("environmental");
        userEnvironmental.setPassword(passwordEncryptor.encryptPassword("environmental"));
        userEnvironmental.setUserType(UserDetailsEnum.EnvironmentalManager.toString());
        knownUsers.put("environmental", userEnvironmental);

        UserDetails userAdmin = new UserDetails();
        userAdmin.setUsername("admin");
        userAdmin.setPassword(passwordEncryptor.encryptPassword("admin"));
        userAdmin.setUserType("Admin");
        knownUsers.put("admin", userAdmin);
    }


    @Override
    public UserDetails loadUserByUsername(String username, String password) {
        loadUsers();
        UserDetails user = knownUsers.get(username.trim());
        if (user == null)
            return null; // Usuari no trobat
        // Contrasenya
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
            return user;
        }
        else {
            return null; // bad login!
        }
    }

    @Override
    public Collection<UserDetails> listAllUsers() {
        return knownUsers.values();
    }
}
