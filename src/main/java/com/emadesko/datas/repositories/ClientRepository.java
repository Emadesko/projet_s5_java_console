package com.emadesko.datas.repositories;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Client;

public interface ClientRepository extends Repository<Client>{
    Client getClientByTelephone(String telephone);
    Client getClientBySurnom(String surnom);
}
