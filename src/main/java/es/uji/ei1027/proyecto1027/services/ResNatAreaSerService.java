package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.model.ResNatAreaService;
import es.uji.ei1027.proyecto1027.model.Service;

import java.util.List;
import java.util.Map;

public interface ResNatAreaSerService {
    public List<ResNatAreaService> getResNatAreaServiceByArea(String code_area);
    public List<String> getAllServices();
}
