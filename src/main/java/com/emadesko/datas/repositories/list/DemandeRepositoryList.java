package com.emadesko.datas.repositories.list;

import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Demande;
import com.emadesko.datas.enums.Etat;
import com.emadesko.datas.repositories.DemandeRepository;

public class DemandeRepositoryList extends DetteMereRepositoryList<Demande> implements DemandeRepository {

    @Override
    public List<Demande> getDemandesByEtat(Etat etat) {
        return super.select().stream().filter(demande -> demande.getEtat() == etat ).toList();
    }
    
    @Override
    public List<Demande> getDemandesByEtatAndClient(Etat etat, Client client) {
        return super.select().stream().filter(demande -> demande.getEtat() == etat && demande.getClient()==client ).toList();
    }
}
