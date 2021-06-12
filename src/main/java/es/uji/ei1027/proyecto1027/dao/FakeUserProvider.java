package es.uji.ei1027.proyecto1027.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.proyecto1027.model.UserDetails;

@Repository
public class FakeUserProvider implements UserDao {
    final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();

    public FakeUserProvider() {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        UserDetails userClient = new UserDetails();
        userClient.setUsername("citizen");
        userClient.setPassword(passwordEncryptor.encryptPassword("citizen"));
        userClient.setUserType("Citizen");
        knownUsers.put("citizen", userClient);

        UserDetails userController = new UserDetails();
        userController.setUsername("controller");
        userController.setPassword(passwordEncryptor.encryptPassword("controller"));
        userController.setUserType("Controller");
        knownUsers.put("controller", userController);

        UserDetails userMunicipal = new UserDetails();
        userMunicipal.setUsername("municipal");
        userMunicipal.setPassword(passwordEncryptor.encryptPassword("municipal"));
        userMunicipal.setUserType("MunicipalManager");
        knownUsers.put("municipal", userMunicipal);

        UserDetails userEnvironmental = new UserDetails();
        userEnvironmental.setUsername("environmental");
        userEnvironmental.setPassword(passwordEncryptor.encryptPassword("environmental"));
        userEnvironmental.setUserType("EnvironmentalManager");
        knownUsers.put("environmental", userEnvironmental);

        UserDetails userAdmin = new UserDetails();
        userEnvironmental.setUsername("admin");
        userEnvironmental.setPassword(passwordEncryptor.encryptPassword("admin"));
        userEnvironmental.setUserType("Admin");
        knownUsers.put("admin", userEnvironmental);
    }

    @Override
    public UserDetails loadUserByUsername(String username, String password) {
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
