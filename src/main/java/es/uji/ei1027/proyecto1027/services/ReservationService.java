package es.uji.ei1027.proyecto1027.services;

import es.uji.ei1027.proyecto1027.model.NaturalArea;
import es.uji.ei1027.proyecto1027.model.Zone;

import java.util.List;

public interface ReservationService {
    public List<NaturalArea> getAllNaturalAreas();
    public List<Zone> getAllZonesPerArea(String codeArea);
}
