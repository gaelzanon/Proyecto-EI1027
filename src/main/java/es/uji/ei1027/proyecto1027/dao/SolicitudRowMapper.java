package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.model.Solicitud;
import es.uji.ei1027.proyecto1027.model.TypeNaturalArea;
import es.uji.ei1027.proyecto1027.model.TypeService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class SolicitudRowMapper implements RowMapper<Solicitud> {
    public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Solicitud solicitud = new Solicitud();
        solicitud.setCode(rs.getString( "code" ));
        solicitud.setName(rs.getString( "name" ));
        solicitud.setDescripcion(rs.getString( "description" ));
        solicitud.setFecha(rs.getObject("Fecha", LocalDate.class ));
        return solicitud;
    }
}