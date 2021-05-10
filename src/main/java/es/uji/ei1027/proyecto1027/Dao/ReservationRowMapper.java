package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.proyecto1027.model.Reservation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationRowMapper implements RowMapper<Reservation> {
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reservation reservation=new Reservation();
        reservation.setAddress(rs.getString("address"));
        reservation.setDate(LocalDate.parse(rs.getString("date"), DateTimeFormatter.ofPattern("d/MM/yyyy")));
        reservation.setQr(rs.getString("qr"));
        reservation.setCode(rs.getString("code"));
        reservation.setCreationDate(LocalDate.parse(rs.getString("creation_date"), DateTimeFormatter.ofPattern("d/MM/yyyy")));
        reservation.setNifCitizen(rs.getString("nif_citizen"));
        reservation.setState(rs.getString("state"));
        reservation.setStartTime(LocalDate.parse(rs.getString("start_time"), DateTimeFormatter.ofPattern("d/MM/yyyy")));
        reservation.setEndTime(LocalDate.parse(rs.getString("end_time"), DateTimeFormatter.ofPattern("d/MM/yyyy")));
        reservation.setCols(rs.getInt("cols"));
        reservation.setRow(rs.getInt("row"));
        reservation.setCodeArea(rs.getString("code_area"));
        return reservation;
    }
}
