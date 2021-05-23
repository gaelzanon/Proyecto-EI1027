package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.model.TypeNaturalArea;
import es.uji.ei1027.proyecto1027.model.TypeService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TypeAreaRowMapper implements RowMapper<TypeNaturalArea> {
    public TypeNaturalArea mapRow(ResultSet rs, int rowNum) throws SQLException
{
TypeNaturalArea typeNaturalArea = new TypeNaturalArea();
typeNaturalArea.setType(rs.getString( "type" ));
return typeNaturalArea;
}
}