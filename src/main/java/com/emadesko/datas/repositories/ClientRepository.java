package com.emadesko.datas.repositories;

import java.util.List;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Compte;


public interface ClientRepository extends Repository<Client>{
    Client getClientByTelephone(String telephone);
    Client getClientBySurnom(String surnom);
    Client getClientByCompte(Compte compte);
    List <Client> getClientsByAccountStatus(boolean with);
}
