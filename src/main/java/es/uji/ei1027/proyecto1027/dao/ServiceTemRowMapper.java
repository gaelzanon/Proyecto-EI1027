package es.uji.ei1027.proyecto1027.dao;


import es.uji.ei1027.proyecto1027.model.Service;
import es.uji.ei1027.proyecto1027.model.ServiceTem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ServiceTemRowMapper implements RowMapper<ServiceTem> {
    public ServiceTem mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        ServiceTem serviceTem = new ServiceTem();
        serviceTem.setCode(rs.getString( "code" ));
        serviceTem.setType_of_service(rs.getString( "type_of_service" ));
        serviceTem.setDescription(rs.getString( "description" ));
        serviceTem.setFechaReg(rs.getObject("registration_date", LocalDate.class));
        serviceTem.setFechaEnd(rs.getObject("end_date", LocalDate.class));
        return serviceTem;
    }
}