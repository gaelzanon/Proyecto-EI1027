package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.Controller;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerRowMapper implements RowMapper<Controller> {
    public Controller mapRow(ResultSet rs, int rowNum) throws SQLException {
        Controller controller = new Controller();
        controller.setNIF(rs.getString("nif"));
        controller.setPassword(rs.getString("password"));
        controller.setTipoUsuario(rs.getString("tipoUsuario"));
        controller.setName(rs.getString("name"));
        controller.setSurname(rs.getString("surname"));
        controller.setCode_area(rs.getString("code_area"));
        controller.setEmail(rs.getString("email"));

        return controller;
    }
}