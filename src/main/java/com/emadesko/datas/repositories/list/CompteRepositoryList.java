package com.emadesko.datas.repositories.list;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryList;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.enums.Role;
import com.emadesko.datas.repositories.CompteRepository;

public class CompteRepositoryList extends RepositoryList<Compte> implements CompteRepository{

    @Override
    public Compte getCompteByLogin(String login) {
        return super.select().stream().filter(compte->compte.getLogin().compareTo(login)==0).findFirst().orElse(null);
    }
    @Override
    public Compte getCompteByEmail(String email) {
        return super.select().stream().filter(compte->compte.getEmail().compareTo(email)==0).findFirst().orElse(null);
    }
    @Override
    public List<Compte> getComptesByRole(Role role) {
        return super.select().stream().filter(compte->compte.getRole()==role).toList();
    }
    @Override
    public List<Compte> getComptesByEtat(boolean isActive) {
        return super.select().stream().filter(compte->compte.isActive()==isActive).toList();
    }

}
