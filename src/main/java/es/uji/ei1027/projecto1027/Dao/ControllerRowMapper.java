package es.uji.ei1027.projecto1027.Dao;

import es.uji.ei1027.projecto1027.model.Citizen;
import es.uji.ei1027.projecto1027.model.Controller;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControllerRowMapper implements RowMapper<Controller> {
    public Controller mapRow(ResultSet rs, int rowNum) throws SQLException {
        Controller controller = new Controller();
        controller.setNIF(rs.getString("nif"));
        controller.setName(rs.getString("name"));
        controller.setSurname(rs.getString("surname"));
        controller.setCode_area(rs.getString("code_area"));
        controller.setEmail(rs.getString("email"));

        return controller;
    }
}