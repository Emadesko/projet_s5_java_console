package com.emadesko.datas.repositories.jpa;

import com.emadesko.datas.entities.DetailDemande;
import com.emadesko.datas.repositories.DetailDemandeRepository;

public class DetailDemandeRepositoryJpa extends DetailMereRepositoryJpa<DetailDemande> implements DetailDemandeRepository{
    
    public DetailDemandeRepositoryJpa(){
        super(DetailDemande.class,"demande_id");
    }
}
