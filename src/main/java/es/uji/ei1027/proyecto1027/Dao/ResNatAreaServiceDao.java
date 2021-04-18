package es.uji.ei1027.proyecto1027.Dao;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResNatAreaServiceDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añadir ResNatAreaService a la clase de datos */
    public void addR_NArea_service(ResNatAreaService res_na_ser) {
        jdbcTemplate.update("INSERT INTO ResNatAreaService VALUES(?, ?)", res_na_ser.getAreaCode(), res_na_ser.getCode());
    }

    /* Eliminar ResNatAreaService de la base de datos */
    public void deleteR_NArea_service(String areaCode, String code) {
        jdbcTemplate.update("DELETE from ResNatAreaService where code_area=? AND code=?", areaCode, code);
    }

    /* Obtener un ResNatAreaService */
    public ResNatAreaService getR_NArea_service(String areaCode, String code) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from ResNatAreaService where cols=? and rows=? and code_area", new ResNatAreaServiceRowMapper(), areaCode, code);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtener todos los ResNatAreaService */
    public List<ResNatAreaService> getR_NArea_services() {
        try {
            return jdbcTemplate.query("SELECT * from ResNatAreaService", new ResNatAreaServiceRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<ResNatAreaService>();
        }
    }

}