package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.MunicipalityDao;
import es.uji.ei1027.proyecto1027.model.Municipality;
import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MunicipalityManagerSvc implements MunicipalityManagerService {

    @Autowired
    MunicipalityDao municipalityDao;

    @Override
    public List<Municipality> getAllMunicipalities() {
        return municipalityDao.getMunicipality();
    }
}
