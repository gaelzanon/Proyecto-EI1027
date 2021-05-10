package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.NaturalArea;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NaturalAreaRowMapper implements RowMapper<NaturalArea> {
    public NaturalArea mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        NaturalArea naturalArea = new NaturalArea();
        naturalArea.setCodeArea(rs.getString("code_area"));
        naturalArea.setName(rs.getString("name"));
        naturalArea.setAddress(rs.getString("address"));
        naturalArea.setCurrentCapacity(rs.getInt("current_capacity"));
        naturalArea.setMaxCapacity(rs.getInt("max_capacity"));
        naturalArea.setDescripcion(rs.getString("description"));
        naturalArea.setType(rs.getString("type"));
        naturalArea.setRegistrationDate(LocalDate.parse(rs.getString("reg_date"), DateTimeFormatter.ofPattern("d/MM/yyyy")));
        naturalArea.setState(rs.getString("state"));
        naturalArea.setStartTime(LocalDate.parse(rs.getString("start_time"), DateTimeFormatter.ofPattern("d/MM/yyyy")));
        naturalArea.setEndTime(LocalDate.parse(rs.getString("end_time"), DateTimeFormatter.ofPattern("d/MM/yyyy")));
        naturalArea.setMunCode(rs.getString("mun_code"));
        return naturalArea;
    }
}
