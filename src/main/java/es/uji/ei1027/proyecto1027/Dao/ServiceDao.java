package es.uji.ei1027.proyecto1027.Dao;


import es.uji.ei1027.clubesportiu.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        "INSERT INTO service VALUES(?, ?, ?, ?, ?)" ,
        service.getCode() , service.getType() , service.getDescription() ,
        service.getInitial_Date());
	}catch(Exception e ){
		System.out.println("Entrada duplicada no se ha podido insertar");
		
	}    
}

/* Esborra  */
public void deleteService(Service service) {
    jdbcTemplate.update(
    "DELETE FROM service WHERE nom = ?",
    service.getCode());
}

/* Actualitza
(excepte el code, que és la clau primària) */
public void updateService(Service service) {
    jdbcTemplate.update(
    "UPDATE service SET type=?,  description =? , innitial_date =?   WHERE code = ?",
    service.getType(), service.getDescription(), service.getInitial_Date() , service.getCode() );
}

/* listar */
public List<Service> getServices() {
    try {
        return jdbcTemplate.query("select * from service",new ServiceRowMapper());
    }catch (EmptyResultDataAccessException e) {
        return new ArrayList<Service>();
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