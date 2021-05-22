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
        UserDetails userDavid = new UserDetails();
        userDavid.setUsername("david");
        userDavid.setPassword(passwordEncryptor.encryptPassword("david"));
        knownUsers.put("david", userDavid);

        UserDetails userFelipe = new UserDetails();
        userFelipe.setUsername("felipe");
        userFelipe.setPassword(passwordEncryptor.encryptPassword("felipe"));
        knownUsers.put("felipe", userFelipe);

        UserDetails userAdmin = new UserDetails();
        userAdmin.setUsername("admin");
        userAdmin.setPassword(passwordEncryptor.encryptPassword("admin"));
        knownUsers.put("admin", userAdmin);
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
