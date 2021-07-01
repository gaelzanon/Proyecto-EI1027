package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.Controller;
import es.uji.ei1027.proyecto1027.model.EnvironmentalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnvironmentalManagerDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void addEnvironmentalManager(EnvironmentalManager environmentalManager) {
        jdbcTemplate.update(
                "INSERT INTO Environmental_manager VALUES(?, ?, ?, ?, ?, ?)",
                environmentalManager.getNIF(),environmentalManager.getName(),environmentalManager.getSurname(),environmentalManager.getEmail(), environmentalManager.getPassword(), environmentalManager.getTipoUsuario());
    }


    public void deleteEnvironmentalManager(EnvironmentalManager environmentalManager) {
        if (getEnvironmentalManager(environmentalManager.getNIF())!=null){
            //Se puede hacer delete
            String SQL = "DELETE from Environmental_manager where NIF = ?";
            jdbcTemplate.update(SQL,environmentalManager.getNIF());
        }
    }
    public void deleteEnvironmentalManager(String NIF) {
        if (getEnvironmentalManager(NIF)!=null){
            //Se puede hacer delete
            String SQL = "DELETE from Environmental_manager where NIF = ?";
            jdbcTemplate.update(SQL,NIF);
        }
    }


    public void updateEnvironmentalManager(EnvironmentalManager environmentalManager) {

        String SQL = "update Environmental_manager set name = ?, surname = ?, email = ?, password = ? where NIF = ?";
        jdbcTemplate.update(SQL, environmentalManager.getName(),environmentalManager.getSurname(),environmentalManager.getEmail(), environmentalManager.getPassword(), environmentalManager.getNIF());

    }

    public EnvironmentalManager getEnvironmentalManager(String NIF) {
        try {

            EnvironmentalManager c = jdbcTemplate.queryForObject(
                    "SELECT * FROM Environmental_manager WHERE NIF =?",
                    new EnvironmentalManagerRowMapper(),
                    NIF);
            return c;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }


    public List<EnvironmentalManager> getEnvironmentalManagers() {
        try {
            return jdbcTemplate.query("SELECT * FROM Environmental_manager",new EnvironmentalManagerRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<EnvironmentalManager>();
        }
    }
}
