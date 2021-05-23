package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.model.NaturalArea;
import es.uji.ei1027.proyecto1027.model.Service;
import es.uji.ei1027.proyecto1027.model.TypeNaturalArea;
import es.uji.ei1027.proyecto1027.model.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class TypeAreaDao {
private JdbcTemplate jdbcTemplate ;

// Obté el jdbcTemplate a partir del Data Source
@Autowired
public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
}

// add service
public void addTypeArea(TypeNaturalArea typeNaturalArea) {
	try {
        jdbcTemplate.update( 
        "INSERT INTO Type_of_area VALUES(?)" ,
        typeNaturalArea.getType());
	}catch(Exception e ){
		System.out.println("Entrada duplicada no se ha podido insertar");
		
	}    
}

/* Esborra  */
    public void deleteTypeArea(TypeNaturalArea typeNaturalArea) {
        jdbcTemplate.update(
        "DELETE FROM Type_of_area WHERE type = ?",
        typeNaturalArea.getType());
}

    /* Esborra  */
    public void deleteTypeArea(String type) {
        if (getTypeArea(type)!=null){
            //Se puede hacer delete
            String SQL = "DELETE FROM Type_of_area WHERE type = ?";
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
public List<TypeNaturalArea> getTypeAreas() {
    try {
        return jdbcTemplate.query("select * from Type_of_area",new TypeAreaRowMapper());
    }catch (EmptyResultDataAccessException e) {
        return new ArrayList<TypeNaturalArea>();
    }
}

/* Obté el service amb el nom donat. Torna null si no existeix. */
public NaturalArea getTypeArea(String codeArea) {
    try {
        return jdbcTemplate.queryForObject("select * FROM Type_of_area WHERE type =?",
        		new NaturalAreaRowMapper(), codeArea);
    }catch (EmptyResultDataAccessException e) {
        return null;
    }
}
}