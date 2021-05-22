package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.ResNatAreaServiceDao;
import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResNatAreaSerSvc implements ResNatAreaSerService {

    @Autowired
    ResNatAreaServiceDao resNatAreaServiceDao;

    @Override
    public List<ResNatAreaService> getResNatAreaServiceByArea(String code_area) {
        List<ResNatAreaService> serPorArea = resNatAreaServiceDao.getResNatAreaServicePorArea(code_area);
        return serPorArea;
    }
}
