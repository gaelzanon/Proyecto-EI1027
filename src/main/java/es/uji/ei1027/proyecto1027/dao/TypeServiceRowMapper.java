package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.model.Service;
import es.uji.ei1027.proyecto1027.model.TypeService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TypeServiceRowMapper implements RowMapper<TypeService> {
    public TypeService mapRow(ResultSet rs, int rowNum) throws SQLException
{
TypeService typeService = new TypeService();
typeService.setType(rs.getString( "type" ));
return typeService;
}
}