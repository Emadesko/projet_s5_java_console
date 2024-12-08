package com.emadesko.datas.repositories.list;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryList;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.repositories.DetteRepository;

public class DetteRepositoryList extends RepositoryList<Dette> implements DetteRepository {

    @Override
    public List<Dette> getDettesNonSoldesByClient(Client client) {
        return super.select().stream().filter(dette -> dette.getClient() == client && !dette.isSolde() ).toList();
    }
}
