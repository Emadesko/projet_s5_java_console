package com.emadesko.datas.repositories;

import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Demande;
import com.emadesko.datas.enums.Etat;

public interface DemandeRepository extends DetteMereRepository<Demande>{
    List<Demande> getDemandesByEtat(Etat etat);
    List<Demande> getDemandesByEtatAndClient(Etat etat,Client client);
}