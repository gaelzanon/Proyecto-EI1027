package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.Municipality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MunicipalityDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix un municipi a la base de dades */
    public void addMunicipality(Municipality municipality) {
        jdbcTemplate.update("INSERT INTO municipality VALUES(?, ?, ?)",
                municipality.getCode(), municipality.getName(), municipality.getRegistrationDate());
    }

    /* Esborra un municipi de la base de dades */
    public void deleteMunicipality(String codeMunicipality) {
        jdbcTemplate.update("DELETE from municipality where code=?",
                codeMunicipality);
    }

    public void deleteMunicipality(Municipality municipality) {
        jdbcTemplate.update("DELETE from municipality where code=?",
                municipality.getCode());
    }

    /* Actualitza un municipi en la base de dades */
    public void updateMunicipality(Municipality municipality) {
        jdbcTemplate.update("UPDATE municipality SET name=?, registration_date=? where code=?",
                municipality.getName(), municipality.getRegistrationDate(), municipality.getCode());
    }

    /* Obté el municipi amb el codi donat. Torna null si no existeix. */
    public Municipality getMunicipality(String codeMunicipality) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from municipality WHERE code=?",
                    new MunicipalityRowMapper(), codeMunicipality);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /* Obté tots els municipis. Torna una llista buida si no n'hi ha cap. */
    public List<Municipality> getMunicipality() {

        try {

            return jdbcTemplate.query("SELECT * from municipality",
                    new MunicipalityRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Municipality>();
        }
    }

    public List<String> getMunicipalityNames() {
        try {
            List<String> res=new ArrayList<String>();
            List<Municipality> lista = jdbcTemplate.query("SELECT * from municipality",
                    new MunicipalityRowMapper());


            for (int i = 0; i < lista.size(); i++) {
                res.add(lista.get(i).getName());
            }
            return res;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }
    public String getMunicipalityCode(String name){
        try{
            List<Municipality> lista = jdbcTemplate.query("SELECT * from municipality WHERE name=?",
                    new MunicipalityRowMapper(),name );
            return lista.get(0).getCode();
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
    }

}
