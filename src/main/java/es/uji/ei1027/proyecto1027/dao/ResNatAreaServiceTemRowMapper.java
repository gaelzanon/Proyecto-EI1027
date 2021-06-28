package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.model.ResNatAreaServiceTem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ResNatAreaServiceTemRowMapper implements RowMapper<ResNatAreaServiceTem> {

    public ResNatAreaServiceTem mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResNatAreaServiceTem res_natArea_ser = new ResNatAreaServiceTem();
        res_natArea_ser.setCode_area(rs.getString("code_area"));
        res_natArea_ser.setCode(rs.getString("codeTem"));
        res_natArea_ser.setStartTime(rs.getObject("start_time", LocalDate.class));
        res_natArea_ser.setEndTime(rs.getObject("end_time",LocalDate.class));
        return res_natArea_ser;
    }
}