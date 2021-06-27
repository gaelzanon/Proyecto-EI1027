package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResNatAreaServiceDao {
    private JdbcTemplate jdbcTemplate;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)

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
        jdbcTemplate.update("DELETE from R_NArea_Services where code_area=? AND code=? AND start_time=? AND end_time=?", code_area, code, start_time, end_time);
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
    public List<String> getCodes() {
        try {
            List<String> res=new ArrayList<String>();
            List<ResNatAreaService> lista = jdbcTemplate.query("SELECT * from r_narea_services",
                    new ResNatAreaServiceRowMapper());


            for (int i = 0; i < lista.size(); i++) {
                res.add(lista.get(i).getCode());
            }
            return res;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }
    public List<String> getCodesArea() {
        try {
            List<String> res=new ArrayList<String>();
            List<ResNatAreaService> lista = jdbcTemplate.query("SELECT * from r_narea_services",
                    new ResNatAreaServiceRowMapper());


            for (int i = 0; i < lista.size(); i++) {
                res.add(lista.get(i).getCode_area());
            }
            return res;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }


}