package com.emadesko.datas.repositories;

import java.util.List;


import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.enums.Role;

public interface CompteRepository extends Repository<Compte>{
    Compte getCompteByLogin(String login);
    Compte getCompteByEmail(String email);
    List<Compte> getComptesByRole(Role role);
    List<Compte> getComptesByEtat(boolean isActive);
}
