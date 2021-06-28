package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.model.Service;
import es.uji.ei1027.proyecto1027.model.ServiceTem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class ServiceTemDao {
    private JdbcTemplate jdbcTemplate ;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // add service
    public void addServiceTem(ServiceTem serviceTem) {

        jdbcTemplate.update(
                "INSERT INTO serviceTem VALUES(?, ?, ?, ?, ?)" ,
                serviceTem.getCode() , serviceTem.getType_of_service() , serviceTem.getDescription() , LocalDate.now(), serviceTem.getFechaEnd());
    }



    /* Esborra  */
    public void deleteServiceTem(ServiceTem serviceTem) {
        jdbcTemplate.update(
                "DELETE FROM serviceTem WHERE code = ?",
                serviceTem.getCode());
    }

    /* Esborra  */
    public void deleteServiceTem(String code) {
        if (getServiceTem(code)!=null){
            //Se puede hacer delete
            String SQL = "DELETE FROM serviceTem WHERE code = ?";
            jdbcTemplate.update(SQL,code);
        }
    }

    /* Actualitza
    (excepte el code, que és la clau primària) */
    public void updateServiceTem(ServiceTem serviceTem) {
        jdbcTemplate.update(
                "UPDATE service SET type_of_service=?,  description =? , registration_date=?, end_date=?  WHERE code = ?",
                serviceTem.getType_of_service(), serviceTem.getDescription(), serviceTem.getFechaReg(),serviceTem.getFechaEnd() ,  serviceTem.getCode() );

    }

    /* listar */
    public List<ServiceTem> getServicesTem() {
        try {
            return jdbcTemplate.query("select * from serviceTem",new ServiceTemRowMapper());
        }catch (EmptyResultDataAccessException e) {
            return new ArrayList<ServiceTem>();
        }
    }

    /* código de servicios */
    public List<String> getServiceCodes() {
        try {
            return jdbcTemplate.queryForList("select code from serviceTem", String.class);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }

    /* Obté el service amb el nom donat. Torna null si no existeix. */
    public ServiceTem getServiceTem(String codeService) {
        try {
            return jdbcTemplate.queryForObject("select * FROM serviceTem WHERE code =?",
                    new ServiceTemRowMapper(), codeService);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<String> getServiceTemNames() {
        try {
            List<String> res=new ArrayList<String>();
            List<ServiceTem> lista = jdbcTemplate.query("SELECT * from ServiceTem",
                    new ServiceTemRowMapper());


            for (int i = 0; i < lista.size(); i++) {
                res.add(lista.get(i).getType_of_service());
            }
            return res;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }
    public String getServiceTemCode(String name){
        try{
            List<ServiceTem> lista = jdbcTemplate.query("SELECT * from ServiceTem WHERE Type_of_service=?",
                    new ServiceTemRowMapper(),name );
            return lista.get(0).getCode();
        } catch (EmptyResultDataAccessException e) {
            return new String();
        }
    }
    public List<String> getServicesTemTypes() {
        try {
            List<String> res=new ArrayList<String>();
            List<ServiceTem> lista = jdbcTemplate.query("SELECT * from ServiceTem",
                    new ServiceTemRowMapper());


            for (int i = 0; i < lista.size(); i++) {
                res.add(lista.get(i).getType_of_service());
            }
            return res;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }
    public boolean getServiceTemEnd(String code, LocalDate fecha){
        try{
            List<ServiceTem> lista = jdbcTemplate.query("SELECT * from ServiceTem WHERE codeTem=?",
                    new ServiceTemRowMapper(),code );
            if(fecha.compareTo(lista.get(0).getFechaEnd())>=0){
                return true;
            }
            else{return false;}
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

}