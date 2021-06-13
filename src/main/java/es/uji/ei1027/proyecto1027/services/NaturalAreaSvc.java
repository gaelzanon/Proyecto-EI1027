package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.ZoneDao;
import es.uji.ei1027.proyecto1027.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NaturalAreaSvc implements NaturalAreaService {

    @Autowired
    ZoneDao zoneDao;

    @Override
    public List<Zone> getAllNatAreaZones(String code_area) {
        return zoneDao.getZonesArea(code_area);
    }
}
