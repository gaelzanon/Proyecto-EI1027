package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResNatAreaServiceRowMapper implements RowMapper<ResNatAreaService> {

    public ResNatAreaService mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResNatAreaService res_natArea_ser = new ResNatAreaService();
        res_natArea_ser.setAreaCode(rs.getString("code_area"));
        res_natArea_ser.setCode(rs.getString("code"));
        return res_natArea_ser;
    }
}
