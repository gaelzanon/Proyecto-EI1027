package es.uji.ei1027.proyecto1027.Dao;

import es.uji.ei1027.proyecto1027.model.NaturalArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NaturalAreaDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addNaturalArea(NaturalArea naturalArea) {
        jdbcTemplate.update("INSERT INTO Natural_Area VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                naturalArea.getCodeArea(), naturalArea.getName(), naturalArea.getAddress(), naturalArea.getCurrentCapacity(),
                naturalArea.getMaxCapacity(), naturalArea.getDescripcion(), naturalArea.getType(), naturalArea.getRegistrationDate(),
                 naturalArea.getState(), naturalArea.getStartTime(), naturalArea.getEndTime(), naturalArea.getMunCode());
    }

    public void deleteNaturalArea(String codeNaturalArea) {
        jdbcTemplate.update("DELETE from Natural_Area where code_area=?",
                codeNaturalArea);
    }
    public void deleteNaturalArea(NaturalArea naturalArea) {
        jdbcTemplate.update("DELETE from Natural_Area where code_area=?",
                naturalArea.getCodeArea());
    }

    public void updateNaturalArea(NaturalArea naturalArea) {
        jdbcTemplate.update("UPDATE Natural_Area SET name=?, address=?, current_capacity=?, max_capacity=?, description=?, type=?, reg_date=?, state=?, start_time=?, end_time=?, mun_code=? where code_area=?",
                naturalArea.getName(), naturalArea.getAddress(), naturalArea.getCurrentCapacity(), naturalArea.getMaxCapacity(),
                naturalArea.getDescripcion(), naturalArea.getType(), naturalArea.getRegistrationDate(), naturalArea.getState(),
                naturalArea.getStartTime(), naturalArea.getEndTime(), naturalArea.getMunCode(), naturalArea.getCodeArea());
    }

    public NaturalArea getNaturalArea(String codeNaturalArea) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Natural_Area WHERE code_area=?",
                    new NaturalAreaRowMapper(), codeNaturalArea);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<NaturalArea> getNaturalArea() {
        try {
            return jdbcTemplate.query("SELECT * from Natural_Area",
                    new NaturalAreaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<NaturalArea>();
        }
    }
}
