package com.emadesko.services;

import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.repositories.DetteRepository;

public class DetteService extends DetteMereService<Dette>{
    
    private DetteRepository detteRepository;

    public DetteService(DetteRepository detteRepository) {
        super(detteRepository);
        this.detteRepository = detteRepository;
    }
    
    public List <Dette> getDettesNonSoldesByClient(Client client){
        return this.detteRepository.getDettesNonSoldesByClient(client);
    }

}
