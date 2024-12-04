package com.emadesko.services;

import java.util.List;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Entite;



public class ServiceImpl <T extends Entite> implements Service<T> {

    private Repository<T> repository;

    public ServiceImpl(Repository<T> repository) {
        this.repository = repository;
    }

    @Override
    public void create(T data){
        repository.insert(data);
    }

    @Override
    public List<T> getAll(){
        return repository.select();
    }

    @Override
    public void delete(T data) {
        repository.delete(data);    
    }

    @Override
    public void update(T data) {
       repository.update(data);
    }

    @Override
    public T getById(int id) {
        return repository.getById(id);
    }

    @Override
    public T getById(List<T> datas, int id) {
        return datas.stream().filter(dette->dette.getId()==id).findFirst().orElse(null);
    }


}
