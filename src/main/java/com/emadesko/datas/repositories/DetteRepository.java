package com.emadesko.datas.repositories;

import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Dette;


public interface DetteRepository extends DetteMereRepository <Dette>{
    List<Dette> getDettesNonSoldesByClient(Client client);
}