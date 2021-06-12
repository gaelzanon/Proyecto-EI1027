package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.model.NaturalArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationSvc implements ReservationService {

    @Autowired
    NaturalAreaDao naturalAreaDao;

    @Override
    public List<NaturalArea> getAllNaturalAreas() {
        return naturalAreaDao.getNaturalArea();
    }
}
