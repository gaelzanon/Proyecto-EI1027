package es.uji.ei1027.proyecto1027.Dao;

import es.uji.ei1027.proyecto1027.model.R_NArea_service;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class R_NArea_serviceRowMapper implements RowMapper<R_NArea_service> {

    public R_NArea_service mapRow(ResultSet rs, int rowNum) throws SQLException {
        R_NArea_service res_natArea_ser = new R_NArea_service();
        res_natArea_ser.setAreaCode(rs.getString("code_area"));
        res_natArea_ser.setCode(rs.getString("code"));
        return res_natArea_ser;
    }
}
