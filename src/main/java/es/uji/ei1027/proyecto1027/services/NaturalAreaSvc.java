package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.MunicipalityDao;
import es.uji.ei1027.proyecto1027.dao.MunicipalityManagerDao;
import es.uji.ei1027.proyecto1027.dao.ZoneDao;
import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
import es.uji.ei1027.proyecto1027.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NaturalAreaSvc implements NaturalAreaService {

    @Autowired
    ZoneDao zoneDao;

    @Autowired
    MunicipalityDao municipalityDao;

    @Autowired
    MunicipalityManagerDao municipalityManagerDao;

    @Override
    public List<Zone> getAllNatAreaZones(String code_area) {
        return zoneDao.getZonesArea(code_area);
    }

    @Override
    public String getCodigoMunicipalParaMunicipalManager(String nif) {
        return municipalityDao.getMunicipalityCode(municipalityManagerDao.getMunicipalityManager(nif).getMunicipalityName());
    }

    @Override
    public String getMunicipalityName(String codeMun) {
        return municipalityDao.getMunicipality(codeMun).getName();
    }
}
