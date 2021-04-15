package es.uji.ei1027.proyecto1027.Dao;

import es.uji.ei1027.proyecto1027.model.Citizen;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CitizenRowMapper implements RowMapper<Citizen>{
    public Citizen mapRow(ResultSet rs, int rowNum) throws SQLException {
        Citizen citizen = new Citizen();
        citizen.setNIF(rs.getString("nif"));
        citizen.setName(rs.getString("name"));
        citizen.setSurname(rs.getString("surname"));
        citizen.setAddress(rs.getString("address"));
        citizen.setEmail(rs.getString("email"));
        citizen.setdate_of_birth(rs.getObject("date_of_birth", LocalDate.class));
        citizen.setregistration_date(rs.getObject("registration_date", LocalDate.class));
        return citizen;
    }
}
