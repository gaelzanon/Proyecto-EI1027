package es.uji.ei1027.proyecto1027.dao;

import es.uji.ei1027.clubesportiu.model.PermanentService;
import es.uji.ei1027.clubesportiu.model.TemporalService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PermanentServiceRowMapper implements RowMapper<PermanentService> {
    public PermanentService mapRow(ResultSet rs, int rowNum) throws SQLException
{
PermanentService permanentService = new PermanentService();
permanentService.setCode(rs.getString( "code" ));


return permanentService;
}
}