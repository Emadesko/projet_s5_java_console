package com.emadesko.core.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Entite;


public class RepositoryList <T extends Entite> implements Repository<T>{
    protected List<T> datas=new ArrayList<>();

    @Override
    public void insert(T data){
        datas.add(data);
    }

    @Override
    public List<T> select(){
        return datas;
    }

    @Override
    public T getById(int id) {
        return this.select().stream().filter(client->client.getId()==id).findFirst().orElse(null);
    }
}
