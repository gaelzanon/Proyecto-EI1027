package es.uji.ei1027.proyecto1027.dao;






import es.uji.ei1027.clubesportiu.model.PermanentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class PermanentServiceDao {
private JdbcTemplate jdbcTemplate ;

// Obté el jdbcTemplate a partir del Data Source
@Autowired
public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
}

// add service
public void addService(PermanentService permanentService) {
	try {
        jdbcTemplate.update( 
        "INSERT INTO permanent_service VALUES(?)" ,
                permanentService.getCode());
	}catch(Exception e ){
		System.out.println("Entrada duplicada no se ha podido insertar");
		
	}
}

/* Esborra  */
public void deleteService(PermanentService permanentService) {
    jdbcTemplate.update(
    "DELETE FROM permanent_service WHERE code = ?",
            permanentService.getCode());
}

/* Actualitza
(excepte el code, que és la clau primària) */
public void updateService(PermanentService permanentService) {
    jdbcTemplate.update(
    "UPDATE permanent_service SET code=?    WHERE code = ?",
            permanentService.getCode() );
}

/* listar */
public List<PermanentService> getServices() {
    try {
        return jdbcTemplate.query("select * from permanent_service",new PermanentServiceRowMapper());
    }catch (EmptyResultDataAccessException e) {
        return new ArrayList<PermanentService>();
    }
}

/* Obté el service amb el nom donat. Torna null si no existeix. */
public PermanentService getService(String codeService) {
    try {
        return jdbcTemplate.queryForObject("select * FROM permanent_service WHERE code =?",
        		new PermanentServiceRowMapper(), codeService);
    }catch (EmptyResultDataAccessException e) {
        return null;
    }
}





}