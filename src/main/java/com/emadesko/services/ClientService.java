package com.emadesko.services;

import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.repositories.ClientRepository;

public class ClientService implements Service<Client>{
    
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void create(Client client){
        clientRepository.insert(client);
    }

    public List<Client> getAll(){
        return clientRepository.select();
    }

    public Client getClientByTelephone(String tel){
        return this.clientRepository.getClientByTelephone(tel);
    }

    public Client getClientBySurnom(String surnom){
        return this.clientRepository.getClientBySurnom(surnom);
    }

}
