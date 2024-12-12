package com.emadesko.services;

import java.util.List;

import com.emadesko.datas.entities.DetteMere;
import com.emadesko.datas.entities.Entite;
import com.emadesko.datas.repositories.DetailMereRepository;

public class DetailMereService <T extends Entite> extends ServiceImpl<T>{
    
    private DetailMereRepository <T> detailMereRepository;

    public DetailMereService(DetailMereRepository <T> detailMereRepository) {
        super(detailMereRepository);
        this.detailMereRepository = detailMereRepository;
    }
    
    public List <T> getDetailMeresByClient(DetteMere detteMere){
        return this.detailMereRepository.getDetailMeresByDetteMere(detteMere);
    }


}
