package com.emadesko.datas.repositories.jpa;

import com.emadesko.core.repository.impl.RepositoryJpa;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.repositories.ClientRepository;

public class ClientRepositoryJpa extends RepositoryJpa<Client> implements ClientRepository{
    
    public ClientRepositoryJpa(){
        super(Client.class);
    }

    @Override
    public Client getClientByTelephone(String telephone) {
        return super.selectBy("telephone" + " LIKE :tel", "tel", telephone);
    }

    @Override
    public Client getClientBySurnom(String surnom) {
        return super.selectBy("surname" + " LIKE :surnom", "surnom", surnom);
    }
}
