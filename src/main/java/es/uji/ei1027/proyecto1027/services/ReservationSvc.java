package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.*;
import es.uji.ei1027.proyecto1027.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    MunicipalityManagerDao municipalityManagerDao;

    @Autowired
    MunicipalityDao municipalityDao;

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

    @Override
    public List<String> getAreasForMunicipalManager(String nif) {
        MunicipalityManager municipalityManager = municipalityManagerDao.getMunicipalityManager(nif);
        String codigoMun = municipalityDao.getMunicipalityCode(municipalityManager.getMunicipalityName());
        List<NaturalArea> naturalAreas = naturalAreaDao.getNaturalAreasPorMunicipio(codigoMun);
        List<String> codigosArea = new ArrayList<String>();
        for (NaturalArea natArea : naturalAreas) {
            codigosArea.add(natArea.getCodeArea());
        }
        return codigosArea;
    }
}
