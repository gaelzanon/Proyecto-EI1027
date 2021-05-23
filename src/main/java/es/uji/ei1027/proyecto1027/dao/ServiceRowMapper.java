package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.model.Service;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ServiceRowMapper implements RowMapper<Service> {
    public Service mapRow(ResultSet rs, int rowNum) throws SQLException
{
Service service = new Service();
service.setCode(rs.getString( "code" ));
service.setType_of_service(rs.getString( "type_of_service" ));
service.setDescription(rs.getString( "description" ));
service.setTemp(rs.getBoolean( "temp"));
return service;
}
}