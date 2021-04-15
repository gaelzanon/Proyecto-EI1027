package es.uji.ei1027.proyecto1027.Dao;

import es.uji.ei1027.proyecto1027.model.Municipality;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class MunicipalityRowMapper implements
        RowMapper<Municipality> {

    public Municipality mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Municipality municipality = new Municipality();
        municipality.setCode(rs.getString("code"));
        municipality.setName(rs.getString("name"));
        municipality.setRegistrationDate(rs.getObject("registration_date", LocalDate.class));
        return municipality;
    }
}