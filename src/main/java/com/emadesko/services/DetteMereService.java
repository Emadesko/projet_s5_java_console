package com.emadesko.services;

import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Entite;
import com.emadesko.datas.repositories.DetteMereRepository;

public class DetteMereService <T extends Entite> extends ServiceImpl<T>{
    
    private DetteMereRepository <T> detteMereRepository;

    public DetteMereService(DetteMereRepository <T> detteMereRepository) {
        super(detteMereRepository);
        this.detteMereRepository = detteMereRepository;
    }
    
    public List <T> getDetteMeresByClient(Client client){
        return this.detteMereRepository.getDetteMereByClient(client);
    }


}
