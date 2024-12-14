package com.emadesko.services;

import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.repositories.ClientRepository;

public class ClientService extends ServiceImpl<Client>{
    
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        super(clientRepository);
        this.clientRepository = clientRepository;
    }

    public Client getClientByTelephone(String tel){
        return this.clientRepository.getClientByTelephone(tel);
    }

    public Client getClientBySurnom(String surnom){
        return this.clientRepository.getClientBySurnom(surnom);
    }
    public Client getClientByCompte(Compte compte){
        return this.clientRepository.getClientByCompte(compte);
    }

    public List <Client> getClientsByAccountStatus(boolean with){
        return this.clientRepository.getClientsByAccountStatus(with);
    }

}
