package com.emadesko.datas.repositories.jpa;

import com.emadesko.core.repository.impl.RepositoryJpa;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.repositories.CompteRepository;

public class CompteRepositoryJpa extends RepositoryJpa<Compte> implements CompteRepository{
    
    public CompteRepositoryJpa(){
        super(Compte.class);
    }

    @Override
    public Compte getCompteByLogin(String login) {
        return super.selectBy("login" + " LIKE :login", "login", login);
    }

    @Override
    public Compte getCompteByEmail(String email) {
        return super.selectBy("email" + " LIKE :email", "email", email);
    }

}
