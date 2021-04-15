package es.uji.ei1027.projecto1027.Dao;

import es.uji.ei1027.projecto1027.model.Citizen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CitizenDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void addCitizen(Citizen citizen) {
        jdbcTemplate.update(
                "INSERT INTO Citizen VALUES(?, ?, ?, ?, ?, ?, ?)",
                citizen.getNIF(),citizen.getName(),citizen.getSurname(),citizen.getEmail(), citizen.getAddress(),citizen.getdate_of_birth(),citizen.getregistration_date());
    }


    public void deleteCitizen(Citizen citizen) {
        if (getCitizen(citizen.getNIF())!=null){
            //Se puede hacer delete
            String SQL = "DELETE from Citizen where NIF = ?";
            jdbcTemplate.update(SQL,citizen.getNIF());
        }
    }
    public void deleteCitizen(String NIF) {
        if (getCitizen(NIF)!=null){
            //Se puede hacer delete
            String SQL = "DELETE from Citizen where NIF = ?";
            jdbcTemplate.update(SQL,NIF);
        }
    }


    public void updateCitizen(Citizen citizen) {

        String SQL = "update Citizen set Name = ?, Surname = ?, Address = ?, Email = ?, date_of_birth = ?, registration_date = ? where NIF = ?";
        jdbcTemplate.update(SQL, citizen.getName(),citizen.getSurname(),citizen.getAddress(),citizen.getEmail(),citizen.getdate_of_birth(), citizen.getregistration_date(), citizen.getNIF());

    }

    public Citizen getCitizen(String NIF) {
        try {

            Citizen c = jdbcTemplate.queryForObject(
                    "SELECT * FROM citizen WHERE NIF =?",
                    new CitizenRowMapper(),
                    NIF);
            return c;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }


    public List<Citizen> getCitizens() {
        try {
            return jdbcTemplate.query("SELECT * FROM citizen",new CitizenRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Citizen>();
        }
    }
}
