package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.controller.ProyectoException;
import es.uji.ei1027.proyecto1027.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class SolicitudDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // add service
    public void addSolicitud(Solicitud solicitud) {

        jdbcTemplate.update(
                "INSERT INTO Solicitud VALUES(?,?,?,?)",
                solicitud.getCode(), solicitud.getName(), solicitud.getDescripcion(), LocalDate.now());

    }


    /* Esborra  */
    public void deleteSolicitud(Solicitud solicitud) {
        jdbcTemplate.update(
                "DELETE FROM Solicitud WHERE code = ?",
                solicitud.getCode());
    }

    /* Esborra  */
    public void deleteSolicitud(String code) {
        if (getSolicitud(code) != null) {
            //Se puede hacer delete
            String SQL = "DELETE FROM Solicitud WHERE code = ?";
            jdbcTemplate.update(SQL, code);
        }
    }


    /* listar */
    public List<Solicitud> getSolicituds() {
        try {
            return jdbcTemplate.query("select * from Solicitud", new SolicitudRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Solicitud>();
        }
    }

    /* Obté el service amb el nom donat. Torna null si no existeix. */
    public Solicitud getSolicitud(String code) {
        try {
            return jdbcTemplate.queryForObject("select * FROM Solicitud WHERE code =?",
                    new SolicitudRowMapper(), code);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
