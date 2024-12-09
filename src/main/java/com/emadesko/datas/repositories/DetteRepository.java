package com.emadesko.datas.repositories;

import java.util.List;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Dette;


public interface DetteRepository extends Repository<Dette>{
    List<Dette> getDettesByClient(Client client);
    List<Dette> getDettesNonSoldesByClient(Client client);
    // List<Dette> getDettesByEtat(boolean isArchive);
    // List<Dette> getNonAccountedDettes();
}