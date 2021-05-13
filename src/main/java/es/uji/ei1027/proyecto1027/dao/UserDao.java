package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.UserDetails;

import java.util.Collection;

public interface UserDao {
    UserDetails loadUserByUsername(String username, String password);
    Collection<UserDetails> listAllUsers();
}
