package com.emadesko.datas.repositories.jpa;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryJpa;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Entite;
import com.emadesko.datas.repositories.DetteMereRepository;

public class DetteMereRepositoryJpa <T extends Entite> extends RepositoryJpa<T> implements DetteMereRepository<T>{
    
    public DetteMereRepositoryJpa(Class<T> clazz){
        super(clazz);
    }

    @Override
    public List<T> getDetteMereByClient(Client client) {
        return super.selectManyBy("client_id = :id", "id", client.getId());
    }

}
