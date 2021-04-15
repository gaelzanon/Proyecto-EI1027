package es.uji.ei1027.projecto1027.Dao;

import es.uji.ei1027.projecto1027.model.R_NArea_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class R_NArea_serviceDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añadir R_NArea_service a la clase de datos */
    public void addR_NArea_service(R_NArea_service res_na_ser) {
        jdbcTemplate.update("INSERT INTO R_NArea_service VALUES(?, ?)", res_na_ser.getAreaCode(), res_na_ser.getCode());
    }

    /* Eliminar R_NArea_service de la base de datos */
    public void deleteR_NArea_service(String areaCode, String code) {
        jdbcTemplate.update("DELETE from R_NArea_service where code_area=? AND code=?", areaCode, code);
    }

    /* Obtener un R_NArea_service */
    public R_NArea_service getR_NArea_service(String areaCode, String code) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from R_NArea_service where cols=? and rows=? and code_area", new R_NArea_serviceRowMapper(), areaCode, code);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtener todos los R_NArea_service */
    public List<R_NArea_service> getR_NArea_services() {
        try {
            return jdbcTemplate.query("SELECT * from R_NArea_service", new R_NArea_serviceRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<R_NArea_service>();
        }
    }

}