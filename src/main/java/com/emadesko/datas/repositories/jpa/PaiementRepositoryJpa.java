package com.emadesko.datas.repositories.jpa;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryJpa;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.entities.Paiement;
import com.emadesko.datas.repositories.PaiementRepository;

public class PaiementRepositoryJpa extends RepositoryJpa<Paiement> implements PaiementRepository{
    
    public PaiementRepositoryJpa(){
        super(Paiement.class);
    }

    @Override
    public List<Paiement> getPaiementsByDette(Dette dette) {
        return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE dette_id = :id ", clazz)
            .setParameter("id", dette.getId())
            .getResultList();
        
    }
}
