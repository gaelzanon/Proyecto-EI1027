package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.Controller;
import es.uji.ei1027.proyecto1027.model.EnvironmentalManager;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnvironmentalManagerRowMapper implements RowMapper<EnvironmentalManager> {
    public EnvironmentalManager mapRow(ResultSet rs, int rowNum) throws SQLException {
        EnvironmentalManager environmentalManager = new EnvironmentalManager();
        environmentalManager.setNIF(rs.getString("nif"));
        environmentalManager.setPassword(rs.getString("password"));
        environmentalManager.setTipoUsuario(rs.getString("tipoUsuario"));
        environmentalManager.setName(rs.getString("name"));
        environmentalManager.setSurname(rs.getString("surname"));
        environmentalManager.setEmail(rs.getString("email"));

        return environmentalManager;
    }
}