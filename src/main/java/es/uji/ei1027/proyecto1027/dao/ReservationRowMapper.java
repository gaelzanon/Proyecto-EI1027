package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.Reservation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationRowMapper implements RowMapper<Reservation> {
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reservation reservation=new Reservation();
        reservation.setAddress(rs.getString("address"));
        reservation.setDate(rs.getObject("date", LocalDate.class));
        reservation.setQr(rs.getString("qr"));
        reservation.setCode(rs.getString("code"));
        reservation.setCreationDate(rs.getObject("creation_date", LocalDate.class));
        reservation.setNifCitizen(rs.getString("nif_citizen"));
        reservation.setState(rs.getString("state"));
        reservation.setStartTime(rs.getObject("start_time", LocalTime.class));
        reservation.setEndTime(rs.getObject("end_time", LocalTime.class));
        reservation.setCols(rs.getInt("col"));
        reservation.setRow(rs.getInt("row"));
        reservation.setCodeArea(rs.getString("code_area"));
        return reservation;
    }
}
