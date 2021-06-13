package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.NaturalAreaDao;
import es.uji.ei1027.proyecto1027.model.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneSvc implements ZoneService {

    @Autowired
    NaturalAreaDao naturalAreaDao;

}
