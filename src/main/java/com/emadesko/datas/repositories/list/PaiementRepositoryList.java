package com.emadesko.datas.repositories.list;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryList;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.entities.Paiement;
import com.emadesko.datas.repositories.PaiementRepository;

public class PaiementRepositoryList extends RepositoryList<Paiement> implements PaiementRepository {

    @Override
    public List<Paiement> getPaiementsByDette(Dette dette) {
        return super.select().stream().filter(paiement -> paiement.getDette() == dette).toList();
    }
}
