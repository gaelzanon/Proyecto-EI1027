package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.model.Service;
import es.uji.ei1027.proyecto1027.model.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class TypeServiceDao {
private JdbcTemplate jdbcTemplate ;

// Obté el jdbcTemplate a partir del Data Source
@Autowired
public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
}

// add service
public void addTypeService(TypeService typeService) {
	try {
        jdbcTemplate.update( 
        "INSERT INTO Type_of_service VALUES(?)" ,
        typeService.getType());
	}catch(Exception e ){
		System.out.println("Entrada duplicada no se ha podido insertar");
		
	}    
}

/* Esborra  */
    public void deleteTypeService(TypeService typeService) {
        jdbcTemplate.update(
        "DELETE FROM Type_of_service WHERE type = ?",
        typeService.getType());
}

    /* Esborra  */
    public void deleteTypeService(String type) {
        if (getTypeService(type)!=null){
            //Se puede hacer delete
            String SQL = "DELETE FROM Type_of_service WHERE type = ?";
            jdbcTemplate.update(SQL,type);
        }
    }

/* No se actualiza, solo tiene la clave primaria
public void updateTypeService(TypeService typeService) {
    jdbcTemplate.update(
    "UPDATE Type_of_service SET type=?",
    typeService.getType() );
    System.out.println("No funciona");
}*/

/* listar */
public List<TypeService> getTypeServices() {
    try {
        return jdbcTemplate.query("select * from Type_of_service",new TypeServiceRowMapper());
    }catch (EmptyResultDataAccessException e) {
        return new ArrayList<TypeService>();
    }
}

/* Obté el service amb el nom donat. Torna null si no existeix. */
public TypeService getTypeService(String codeService) {
    try {
        return jdbcTemplate.queryForObject("select * FROM Type_of_service WHERE type =?",
        		new TypeServiceRowMapper(), codeService);
    }catch (EmptyResultDataAccessException e) {
        return null;
    }
}
}