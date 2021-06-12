package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.dao.ZoneDao;
import es.uji.ei1027.proyecto1027.model.NaturalArea;
import es.uji.ei1027.proyecto1027.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationSvc implements ReservationService {

    @Autowired
    NaturalAreaDao naturalAreaDao;

    @Autowired
    ZoneDao zoneDao;

    @Override
    public List<NaturalArea> getAllNaturalAreas() {
        return naturalAreaDao.getNaturalArea();
    }

    @Override
    public List<Zone> getAllZonesPerArea(String codeArea) {
        return zoneDao.getZonesArea(codeArea);
    }
}
