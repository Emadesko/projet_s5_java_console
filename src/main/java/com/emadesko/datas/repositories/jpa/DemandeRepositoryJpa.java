package com.emadesko.datas.repositories.jpa;

import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Demande;
import com.emadesko.datas.enums.Etat;
import com.emadesko.datas.repositories.DemandeRepository;

public class DemandeRepositoryJpa extends DetteMereRepositoryJpa<Demande> implements DemandeRepository{
    
    public DemandeRepositoryJpa(){
        super(Demande.class);
    }

    @Override
    public List<Demande> getDemandesByEtat(Etat etat) {
        return super.selectManyBy("etat = :etat", "etat", etat);
    }

    @Override
    public List<Demande> getDemandesByEtatAndClient(Etat etat, Client client) {
        return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE client_id = :id and etat = :etat", clazz)
        .setParameter("id", client.getId())    
        .setParameter("etat", etat)
        .getResultList();
    }
}
