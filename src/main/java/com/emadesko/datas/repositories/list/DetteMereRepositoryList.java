package com.emadesko.datas.repositories.list;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryList;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Entite;
import com.emadesko.datas.repositories.DetteMereRepository;

public class DetteMereRepositoryList <T extends Entite> extends RepositoryList<T> implements DetteMereRepository<T> {

    @Override
    public List<T> getDetteMereByClient(Client client) {
        return super.select().stream().filter(detteMere -> detteMere.getClient() == client ).toList();
    }


}
