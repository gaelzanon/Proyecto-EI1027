package es.uji.ei1027.proyecto1027.Dao;

import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MunicipalityManagerRowMapper implements RowMapper<MunicipalityManager> {

    public MunicipalityManager mapRow(ResultSet rs, int rowNum) throws SQLException {
        MunicipalityManager munManager = new MunicipalityManager();
        munManager.setNIF(rs.getString("NIF"));
        munManager.setAreaCode(rs.getString("code"));
        munManager.setMunicipalityName(rs.getString("mun_name"));
        munManager.setEmail(rs.getString("email"));
        return munManager;
    }
}
