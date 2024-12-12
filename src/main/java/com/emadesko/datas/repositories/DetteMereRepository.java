package com.emadesko.datas.repositories;

import java.util.List;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Client;


public interface DetteMereRepository <T> extends Repository<T>{
    List<T> getDetteMereByClient(Client client);
}