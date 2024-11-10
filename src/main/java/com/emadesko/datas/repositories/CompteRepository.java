package com.emadesko.datas.repositories;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Compte;

public interface CompteRepository extends Repository<Compte>{
    Compte getCompteByLogin(String login);
    Compte getCompteByEmail(String email);
}
