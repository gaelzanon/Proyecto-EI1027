package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.dao.TypeServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceSvc implements ServiceService{

    @Autowired
    TypeServiceDao typeServiceDao;

    @Override
    public List<String> getAllServiceTypes() {
        return typeServiceDao.getTypeServicesString();
    }
}
