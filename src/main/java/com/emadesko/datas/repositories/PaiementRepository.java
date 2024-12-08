package com.emadesko.datas.repositories;

import java.util.List;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.entities.Paiement;


public interface PaiementRepository extends Repository<Paiement>{
    List <Paiement> getPaiementsByDette(Dette dette);
}
