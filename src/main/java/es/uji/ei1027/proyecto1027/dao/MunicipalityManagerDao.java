package es.uji.ei1027.proyecto1027.dao;
import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MunicipalityManagerDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añadir manager a la clase de datos */
    public void addMunicipalityManager(MunicipalityManager munManager) {
        jdbcTemplate.update("INSERT INTO Municipality_manager VALUES(?, ?, ?, ?, ?, ?, ?)", munManager.getNIF(), munManager.getCode(), munManager.getName(),munManager.getMunicipalityName(), munManager.getEmail(), munManager.getPassword(), munManager.getTipoUsuario());
    }

    /* Eliminar manager de la base de datos */
    public void deleteMunicipalityManager(String NIF) {
        jdbcTemplate.update("DELETE from Municipality_manager where NIF=?", NIF);
    }

    /* Modificar los datos de un manager */
    public void updateMunicipalityManager(MunicipalityManager munManager) {
        jdbcTemplate.update("UPDATE Municipality_manager SET code=?,name=? ,mun_name=?, email=?, password = ? where NIF=?", munManager.getCode(),munManager.getName(), munManager.getMunicipalityName(), munManager.getEmail(), munManager.getPassword(), munManager.getNIF());
    }

    /* Obtener una zona mediante su NIF */
    public MunicipalityManager getMunicipalityManager(String NIF) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Municipality_manager where NIF=?", new MunicipalityManagerRowMapper(), NIF);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obtener todos los managers */
    public List<MunicipalityManager> getMunicipalityManagers() {
        try {
            return jdbcTemplate.query("SELECT * from Municipality_manager", new MunicipalityManagerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<MunicipalityManager>();
        }
    }

}
