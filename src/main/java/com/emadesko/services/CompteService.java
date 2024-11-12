package com.emadesko.services;

import java.util.List;

import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.repositories.CompteRepository;

public class CompteService implements Service<Compte>{

    private CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    public void create(Compte compte){
        compteRepository.insert(compte);
    }

    public List<Compte> getAll(){
        return compteRepository.select();
    }

    public Compte getCompteByLogin(String login){
        return compteRepository.getCompteByLogin(login);
    }
    public Compte getCompteByEmail(String email){
        return compteRepository.getCompteByEmail(email);
    }

    @Override
    public Compte getById(int id) {
        return compteRepository.getById(id);
    }

    @Override
    public Compte getById(List<Compte> datas, int id) {
        return datas.stream().filter(dette->dette.getId()==id).findFirst().orElse(null);
    }

    @Override
    public void delete(Compte data) {
        compteRepository.delete(data);    
    }

    @Override
    public void update(Compte data) {
       compteRepository.update(data);
    }
}
