package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.clubesportiu.model.TemporalService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TemporalServiceRowMapper implements RowMapper<TemporalService> {
    public TemporalService mapRow(ResultSet rs, int rowNum) throws SQLException
{
TemporalService temporalService = new TemporalService();
temporalService.setCode(rs.getString( "code" ));
temporalService.setStartTime(rs.getString( "start_time" ));
temporalService.setEndTime(rs.getString( "end_time" ));

return temporalService;
}
}