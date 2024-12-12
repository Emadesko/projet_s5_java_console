package com.emadesko.datas.repositories.db;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryDb;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.DetteMere;
import com.emadesko.datas.repositories.DetteMereRepository;

public class DetteMereRepositoryDb <T extends DetteMere> extends RepositoryDb<T> implements DetteMereRepository<T>{


    public DetteMereRepositoryDb(String tableName, Class<T> clazz){
        super(tableName, clazz);
    }

    @Override
    public List<T> getDetteMereByClient(Client client) {
        return super.getManyBy("client_id =", client.getId());
    }
}
