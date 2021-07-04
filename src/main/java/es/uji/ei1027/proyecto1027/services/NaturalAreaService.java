package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.model.MunicipalityManager;
import es.uji.ei1027.proyecto1027.model.Zone;

import java.util.List;

public interface NaturalAreaService {
    public List<Zone> getAllNatAreaZones(String code_area);
    public String getCodigoMunicipalParaMunicipalManager(String nif);
    public String getMunicipalityName(String codeMun);
}
