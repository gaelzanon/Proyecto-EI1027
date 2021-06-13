package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.model.Service;
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
public class ServiceDao {
private JdbcTemplate jdbcTemplate ;

// Obté el jdbcTemplate a partir del Data Source
@Autowired
public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
}

// add service
public void addService(Service service) {

        jdbcTemplate.update( 
        "INSERT INTO service VALUES(?, ?, ?, ?, ?)" ,
        service.getCode() , service.getType_of_service() , service.getDescription() , LocalDate.now(),
        service.getTemp());
	}



/* Esborra  */
    public void deleteService(Service service) {
        jdbcTemplate.update(
        "DELETE FROM service WHERE code = ?",
        service.getCode());
}

    /* Esborra  */
    public void deleteService(String code) {
        if (getService(code)!=null){
            //Se puede hacer delete
            String SQL = "DELETE FROM service WHERE code = ?";
            jdbcTemplate.update(SQL,code);
        }
    }

/* Actualitza
(excepte el code, que és la clau primària) */
public void updateService(Service service) {
    jdbcTemplate.update(
    "UPDATE service SET type_of_service=?,  description =? , registration_date=?, temp =?   WHERE code = ?",
    service.getType_of_service(), service.getDescription(), service.getFechaReg(), service.getTemp() , service.getCode() );

}

/* listar */
public List<Service> getServices() {
    try {
        return jdbcTemplate.query("select * from service",new ServiceRowMapper());
    }catch (EmptyResultDataAccessException e) {
        return new ArrayList<Service>();
    }
}

/* código de servicios */
    public List<String> getServiceCodes() {
        try {
            return jdbcTemplate.queryForList("select code from service", String.class);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }

/* Obté el service amb el nom donat. Torna null si no existeix. */
public Service getService(String codeService) {
    try {
        return jdbcTemplate.queryForObject("select * FROM service WHERE code =?",
        		new ServiceRowMapper(), codeService);
    }catch (EmptyResultDataAccessException e) {
        return null;
    }
}
    public List<String> getServiceNames() {
        try {
            List<String> res=new ArrayList<String>();
            List<Service> lista = jdbcTemplate.query("SELECT * from Service",
                    new ServiceRowMapper());


            for (int i = 0; i < lista.size(); i++) {
                res.add(lista.get(i).getType_of_service());
            }
            return res;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }
    public String getServiceCode(String name){
        try{
            List<Service> lista = jdbcTemplate.query("SELECT * from Service WHERE Type_of_service=?",
                    new ServiceRowMapper(),name );
            return lista.get(0).getCode();
        } catch (EmptyResultDataAccessException e) {
            return new String();
        }
    }

}