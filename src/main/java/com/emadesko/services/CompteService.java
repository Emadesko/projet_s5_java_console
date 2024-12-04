package com.emadesko.services;

import java.util.List;

import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.repositories.CompteRepository;

public class CompteService extends ServiceImpl<Compte>{

    private CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        super(compteRepository);
        this.compteRepository = compteRepository;
    }

    public Compte getCompteByLogin(String login){

        return compteRepository.getCompteByLogin(login);
    }
    public Compte getCompteByEmail(String email){
        return compteRepository.getCompteByEmail(email);
    }

    
}
