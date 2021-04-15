package es.uji.ei1027.projecto1027.Dao;

import es.uji.ei1027.projecto1027.model.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ControllerDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void addController(Controller controller) {
        jdbcTemplate.update(
                "INSERT INTO Controller VALUES(?, ?, ?, ?, ?)",
                controller.getNIF(),controller.getName(),controller.getSurname(),controller.getEmail(), controller.getCode_area());
    }


    public void deleteController(Controller controller) {
        if (getController(controller.getNIF())!=null){
            //Se puede hacer delete
            String SQL = "DELETE from Controller where NIF = ?";
            jdbcTemplate.update(SQL,controller.getNIF());
        }
    }
    public void deleteController(String NIF) {
        if (getController(NIF)!=null){
            //Se puede hacer delete
            String SQL = "DELETE from Controller where NIF = ?";
            jdbcTemplate.update(SQL,NIF);
        }
    }


    public void updateController(Controller controller) {

        String SQL = "update Controller set Name = ?, Surname = ?, code_area = ?, Email = ? where NIF = ?";
        jdbcTemplate.update(SQL, controller.getName(),controller.getSurname(),controller.getCode_area(),controller.getEmail(), controller.getNIF());

    }

    public Controller getController(String NIF) {
        try {

            Controller c = jdbcTemplate.queryForObject(
                    "SELECT * FROM Controller WHERE NIF =?",
                    new ControllerRowMapper(),
                    NIF);
            return c;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }


    public List<Controller> getControllers() {
        try {
            return jdbcTemplate.query("SELECT * FROM Controller",new ControllerRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Controller>();
        }
    }
}