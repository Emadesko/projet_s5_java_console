package com.emadesko.datas.repositories.jpa;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryJpa;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.enums.Role;
import com.emadesko.datas.repositories.CompteRepository;

public class CompteRepositoryJpa extends RepositoryJpa<Compte> implements CompteRepository{
    
    public CompteRepositoryJpa(){
        super(Compte.class);
    }

    @Override
    public Compte getCompteByLogin(String login) {
        return super.selectBy("login LIKE :login", "login", login);
    }

    @Override
    public Compte getCompteByEmail(String email) {
        return super.selectBy("email LIKE :email", "email", email);
    }

    @Override
    public List<Compte> getComptesByRole(Role role) {
        return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE role = " + role.ordinal(), clazz).getResultList();
    }

    @Override
    public List<Compte> getComptesByEtat(boolean isActive) {
        return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE isActive = " + (isActive?1:0) , clazz).getResultList();
    }

}
