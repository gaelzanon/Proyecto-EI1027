package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ResNatAreaServiceRowMapper implements RowMapper<ResNatAreaService> {

    public ResNatAreaService mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResNatAreaService res_natArea_ser = new ResNatAreaService();
        res_natArea_ser.setCode_relacion(rs.getString("code_relacion"));
        res_natArea_ser.setCode_area(rs.getString("code_area"));
        res_natArea_ser.setCode(rs.getString("code"));
        res_natArea_ser.setStartTime(rs.getObject("start_time", LocalDate.class));
        res_natArea_ser.setEndTime(rs.getObject("end_time", LocalDate.class));
        return res_natArea_ser;
    }
}
