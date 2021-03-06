package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZoneDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añadir zona a la clase de datos */
    public void addZone(Zone zone) {
        jdbcTemplate.update("INSERT INTO Zone VALUES(?, ?, ?, ?, ?)",zone.getCode(), zone.getCol(), zone.getRow(), zone.getCapacity(), zone.getAreaCode());
    }

    /* Eliminar zona de la base de datos */
    public void deleteZone(String code) {
        jdbcTemplate.update("DELETE from Zone where code=?", code);
    }

    /* Modificar los datos de una zona (suponemos que la posición y el area a la que pertenece nunca cambiarán) */
    public void updateZone(Zone zone) {
        jdbcTemplate.update("UPDATE Zone SET max_capacity=? where code=?", zone.getCapacity(), zone.getCode());
    }

    /* Obtener una zona mediante su fila, columna y area a la que pertenece */
    public Zone getZone(String code) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Zone where code=?", new ZoneRowMapper(), code);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtener todas las zonas */
    public List<Zone> getZones() {
        try {
            return jdbcTemplate.query("SELECT * from Zone", new ZoneRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Zone>();
        }
    }

    /* Obtener todas las zonas de un natural area*/
    public List<Zone> getZonesArea(String codeArea) {
        try {
            return jdbcTemplate.query("SELECT * from Zone WHERE code_area=?", new ZoneRowMapper(), codeArea);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Zone>();
        }
    }

}
