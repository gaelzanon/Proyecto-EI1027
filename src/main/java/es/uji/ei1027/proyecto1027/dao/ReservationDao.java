package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addReservation(Reservation reservation) {
        jdbcTemplate.update("INSERT INTO reservation VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                reservation.getAddress(), reservation.getDate(), reservation.getQr(), reservation.getCode(), reservation.getCreationDate(),
                reservation.getNifCitizen(), reservation.getState(), reservation.getStartTime(), reservation.getEndTime(),
                reservation.getCodeZone(), reservation.getCodeArea());
    }

    public void deleteReservation(String codeReservation) {
        jdbcTemplate.update("DELETE from reservation where code=?",
                codeReservation);
    }
    public void deleteReservation(Reservation reservation) {
        jdbcTemplate.update("DELETE from reservation where code=?",
                reservation.getCode());
    }

    public void updateReservation(Reservation reservation) {
        jdbcTemplate.update("UPDATE reservation SET address=?, date=?, qr=?, creation_date=?, nif_citizen=?, state=?, start_time=?, end_time=?, code_zone=?, code_area=? where code=?",
                reservation.getAddress(), reservation.getDate(), reservation.getQr(), reservation.getCreationDate(), reservation.getNifCitizen(),
                reservation.getState(), reservation.getStartTime(), reservation.getEndTime(), reservation.getCodeZone(),
                reservation.getCodeArea(), reservation.getCode());
    }

    public Reservation getReservation(String codeReservation) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from reservation WHERE code=?",
                    new ReservationRowMapper(), codeReservation);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Reservation> getReservation() {
        try {
            return jdbcTemplate.query("SELECT * from reservation",
                    new ReservationRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Reservation>();
        }
    }

    public List<Reservation> getReservationPerCitizen(String nif) {
        try {
            return jdbcTemplate.query("SELECT * from reservation WHERE nif_citizen=?",
                    new ReservationRowMapper(), nif);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Reservation>();
        }
    }

    public List<Reservation> getReservationPerNaturalArea(String area) {
        try {
            return jdbcTemplate.query("SELECT * from reservation WHERE code_area=?", new ReservationRowMapper(), area);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Reservation>();
        }
    }


}
