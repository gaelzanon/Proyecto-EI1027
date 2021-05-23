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
	try {
        jdbcTemplate.update( 
        "INSERT INTO service VALUES(?, ?, ?, ?)" ,
        service.getCode() , service.getType() , service.getDescription() ,
        service.getInitial_Date());
	}catch(Exception e ){
		System.out.println("Entrada duplicada no se ha podido insertar");
		
	}    
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
    "UPDATE service SET type=?,  description =? , initial_Date =?   WHERE code = ?",
    service.getType(), service.getDescription(), service.getInitial_Date() , service.getCode() );
    System.out.println("No funciona");
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
//            return jdbcTemplate.query("select code from service", new RowMapper<String>() {
//                @Override
//                public String mapRow(ResultSet resultSet, int i) throws SQLException {
//                    return resultSet.getString(1);
//                }
//            });
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





}