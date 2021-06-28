package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.model.ResNatAreaServiceTem;
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
public class ResNatAreaServiceTemDao {
    private JdbcTemplate jdbcTemplate;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añadir ResNatAreaService a la clase de datos */
    public void addR_NArea_serviceTem(ResNatAreaServiceTem res_na_ser) {
        jdbcTemplate.update("INSERT INTO R_NArea_ServicesTem VALUES(?, ?, ?, ?)", res_na_ser.getCode_area(), res_na_ser.getCode(),res_na_ser.getStartTime(),res_na_ser.getEndTime());
    }

    /* Eliminar ResNatAreaService de la base de datos */
    public void deleteR_NArea_serviceTem(String code_area, String code, String start_time, String end_time) {
        jdbcTemplate.update("DELETE from R_NArea_ServicesTem where code_area=? AND codeTem=? AND start_time=? AND end_time=?", code_area, code, start_time, end_time);
    }


    /* Obtener un ResNatAreaService por area */
    public List<ResNatAreaServiceTem> getResNatAreaServiceTemPorArea(String code_area) {
        try {
            return this.jdbcTemplate.query(
                    "SELECT * FROM R_NArea_ServicesTem WHERE code_area=?",
                    new Object[] {code_area}, new ResNatAreaServiceTemRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<ResNatAreaServiceTem>();
        }
    }

    /* Obtener todos los ResNatAreaService */
    public List<ResNatAreaServiceTem> getR_NArea_servicesTem() {
        try {
            return jdbcTemplate.query("SELECT * from R_NArea_ServicesTem", new ResNatAreaServiceTemRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<ResNatAreaServiceTem>();
        }
    }
    public List<String> getCodes() {
        try {
            List<String> res=new ArrayList<String>();
            List<ResNatAreaServiceTem> lista = jdbcTemplate.query("SELECT * from r_narea_servicesTem",
                    new ResNatAreaServiceTemRowMapper());


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
            List<ResNatAreaServiceTem> lista = jdbcTemplate.query("SELECT * from r_narea_servicesTem",
                    new ResNatAreaServiceTemRowMapper());


            for (int i = 0; i < lista.size(); i++) {
                res.add(lista.get(i).getCode_area());
            }
            return res;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }


}