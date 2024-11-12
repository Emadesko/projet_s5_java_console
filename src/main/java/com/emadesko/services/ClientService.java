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

    public List <Client> getNonAccountedClients(){
        return this.clientRepository.getNonAccountedClients();
    }

    @Override
    public Client getById(int id) {
        return this.clientRepository.getById(id);
    }

    @Override
    public Client getById(List<Client> datas, int id) {
        return datas.stream().filter(client->client.getId()==id).findFirst().orElse(null);
    }

    @Override
    public void delete(Client data) {
        clientRepository.delete(data);
    }

    @Override
    public void update(Client data) {
        clientRepository.update(data);
    }

}
