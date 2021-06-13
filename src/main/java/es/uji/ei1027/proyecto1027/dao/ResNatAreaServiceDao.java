package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
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
        jdbcTemplate.update("INSERT INTO R_NArea_Services VALUES(?, ?, ?, ?)", res_na_ser.getCode_area(), res_na_ser.getCode(),res_na_ser.getStartTime(),res_na_ser.getEndTime());
    }

    /* Eliminar ResNatAreaService de la base de datos */
    public void deleteR_NArea_service(String code_area, String code, String start_time, String end_time) {
        jdbcTemplate.update("DELETE from R_NArea_Services where code_area=? AND code=? AND start_time=? AND end_time=?", code_area, code, LocalDate.parse(start_time), LocalDate.parse(end_time));
    }


    /* Obtener un ResNatAreaService por area */
    public List<ResNatAreaService> getResNatAreaServicePorArea(String code_area) {
        try {
            return this.jdbcTemplate.query(
                    "SELECT * FROM R_NArea_Services WHERE code_area=?",
                    new Object[] {code_area}, new ResNatAreaServiceRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<ResNatAreaService>();
        }
    }

    /* Obtener todos los ResNatAreaService */
    public List<ResNatAreaService> getR_NArea_services() {
        try {
            return jdbcTemplate.query("SELECT * from r_narea_services", new ResNatAreaServiceRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<ResNatAreaService>();
        }
    }

}