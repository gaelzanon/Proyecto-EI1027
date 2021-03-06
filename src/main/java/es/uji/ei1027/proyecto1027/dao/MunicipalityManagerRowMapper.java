package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MunicipalityManagerRowMapper implements RowMapper<MunicipalityManager> {

    public MunicipalityManager mapRow(ResultSet rs, int rowNum) throws SQLException {
        MunicipalityManager munManager = new MunicipalityManager();
        munManager.setNIF(rs.getString("NIF"));
        munManager.setPassword(rs.getString("password"));
        munManager.setTipoUsuario(rs.getString("tipoUsuario"));
        munManager.setCode(rs.getString("code"));
        munManager.setName(rs.getString("Name"));
        munManager.setMunicipalityName(rs.getString("mun_name"));
        munManager.setEmail(rs.getString("email"));
        return munManager;
    }
}
