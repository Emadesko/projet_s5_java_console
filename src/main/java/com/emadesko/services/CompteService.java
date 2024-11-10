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
}
