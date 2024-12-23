package com.emadesko.datas.repositories.jpa;

import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.repositories.DetteRepository;

public class DetteRepositoryJpa extends DetteMereRepositoryJpa<Dette> implements DetteRepository{
    
    public DetteRepositoryJpa(){
        super(Dette.class);
    }

    @Override
    public List<Dette> getDettesNonSoldesByClient(Client client) {
        return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE client_id = :id and isSolde = :isSolde", clazz)
            .setParameter("id", client.getId())    
            .setParameter("isSolde", false)    
            .getResultList();
    }

}
