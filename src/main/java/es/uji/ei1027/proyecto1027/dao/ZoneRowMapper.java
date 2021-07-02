package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.Zone;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ZoneRowMapper implements RowMapper<Zone> {

    public Zone mapRow(ResultSet rs, int rowNum) throws SQLException {
        Zone zone = new Zone();
        zone.setCode(rs.getString("code"));
        zone.setCol(rs.getInt("col"));
        zone.setRow(rs.getInt("row"));
        zone.setCapacity(rs.getInt("max_capacity"));
        zone.setAreaCode(rs.getString("code_area"));
        return zone;
    }
}
