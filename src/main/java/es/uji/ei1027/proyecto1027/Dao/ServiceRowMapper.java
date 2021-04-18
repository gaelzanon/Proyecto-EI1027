package es.uji.ei1027.proyecto1027.Dao;


import es.uji.ei1027.proyecto1027.model.Service;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ServiceRowMapper implements RowMapper<Service> {
    public Service mapRow(ResultSet rs, int rowNum) throws SQLException
{
Service service = new Service();
service.setCode(rs.getString( "code" ));
service.setType(rs.getString( "type" ));
service.setDescription(rs.getString( "description" ));
service.setInitial_Date(rs.getString( "initial_date" ));
return service;
}
}