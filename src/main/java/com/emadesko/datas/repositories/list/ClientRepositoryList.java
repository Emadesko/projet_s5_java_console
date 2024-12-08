package com.emadesko.datas.repositories.list;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryList;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.repositories.ClientRepository;

public class ClientRepositoryList extends RepositoryList<Client> implements ClientRepository {

    @Override
    public Client getClientByTelephone(String telephone) {
        return super.select().stream()
                .filter(client -> client.getTelephone().toLowerCase().compareTo(telephone.toLowerCase()) == 0)
                .findFirst().orElse(null);
    }

    @Override
    public Client getClientBySurnom(String surnom) {
        return super.select().stream()
                .filter(client -> client.getSurname().toLowerCase().compareTo(surnom.toLowerCase()) == 0).findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Client> getClientsByAccountStatus(boolean with) {
        
        return super.select().stream().filter(client -> with ? client.getCompte() != null : client.getCompte() == null).toList();
    }
}
