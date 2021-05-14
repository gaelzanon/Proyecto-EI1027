package es.uji.ei1027.proyecto1027.dao;



import es.uji.ei1027.clubesportiu.model.TemporalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class TemporalServiceDao {
private JdbcTemplate jdbcTemplate ;

// Obté el jdbcTemplate a partir del Data Source
@Autowired
public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
}

// add service
public void addService(TemporalService temporalService) {
	try {
        jdbcTemplate.update( 
        "INSERT INTO temporal_service VALUES(?, ?, ?)" ,
        temporalService.getCode() , temporalService.getStartTime() , temporalService.getEndTime());
	}catch(Exception e ){
		System.out.println("Entrada duplicada no se ha podido insertar");
		
	}
}

/* Esborra  */
public void deleteService(TemporalService temporalService) {
    jdbcTemplate.update(
    "DELETE FROM temporal_service WHERE code = ?",
    temporalService.getCode());
}

/* Actualitza
(excepte el code, que és la clau primària) */
public void updateService(TemporalService temporalService) {
    jdbcTemplate.update(
    "UPDATE temporal_service SET start_time=?,  end_time =?    WHERE code = ?",
    temporalService.getStartTime(), temporalService.getEndTime(),  temporalService.getCode() );
}

/* listar */
public List<TemporalService> getServices() {
    try {
        return jdbcTemplate.query("select * from temporal_service",new TemporalServiceRowMapper());
    }catch (EmptyResultDataAccessException e) {
        return new ArrayList<TemporalService>();
    }
}

/* Obté el service amb el nom donat. Torna null si no existeix. */
public TemporalService getService(String codeService) {
    try {
        return jdbcTemplate.queryForObject("select * FROM temporal_service WHERE code =?",
        		new TemporalServiceRowMapper(), codeService);
    }catch (EmptyResultDataAccessException e) {
        return null;
    }
}





}