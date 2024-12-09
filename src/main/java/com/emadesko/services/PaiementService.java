package com.emadesko.services;

import java.util.List;

import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.entities.Paiement;
import com.emadesko.datas.repositories.PaiementRepository;

public class PaiementService extends ServiceImpl<Paiement>{
    
    private PaiementRepository paiementRepository;

    public PaiementService(PaiementRepository paiementRepository) {
        super(paiementRepository);
        this.paiementRepository = paiementRepository;
    }

    public List <Paiement> getPaiementsByDette(Dette dette){
        return this.paiementRepository.getPaiementsByDette(dette);
    }

}
