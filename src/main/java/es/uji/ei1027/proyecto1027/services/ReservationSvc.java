package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.CitizenDao;
import es.uji.ei1027.proyecto1027.dao.ControllerDao;
import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.dao.ZoneDao;
import es.uji.ei1027.proyecto1027.model.Citizen;
import es.uji.ei1027.proyecto1027.model.Controller;
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

    @Autowired
    CitizenDao citizenDao;

    @Autowired
    ControllerDao controllerDao;

    @Override
    public List<NaturalArea> getAllNaturalAreas() {
        return naturalAreaDao.getNaturalArea();
    }

    @Override
    public List<Zone> getAllZonesPerArea(String codeArea) {
        return zoneDao.getZonesArea(codeArea);
    }

    @Override
    public String getAddress(String codeArea) {
        return naturalAreaDao.getNaturalArea(codeArea).getAddress();
    }

    @Override
    public Citizen getCitizen(String nif) {
        return citizenDao.getCitizen(nif);
    }

    @Override
    public String getAreaName(String codeArea) {
        return naturalAreaDao.getNaturalArea(codeArea).getName();
    }

    @Override
    public Zone getZone(String code) {
        return zoneDao.getZone(code);
    }

    @Override
    public Controller getController(String nif) {
        return controllerDao.getController(nif);
    }
}
