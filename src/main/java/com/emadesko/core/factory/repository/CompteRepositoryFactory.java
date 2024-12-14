package com.emadesko.core.factory.repository;

import com.emadesko.core.services.YamlService;
import com.emadesko.datas.enums.RepositoryType;
import com.emadesko.datas.repositories.CompteRepository;
import com.emadesko.datas.repositories.db.CompteRepositoryDb;
import com.emadesko.datas.repositories.jpa.CompteRepositoryJpa;
import com.emadesko.datas.repositories.list.CompteRepositoryList;

public abstract class CompteRepositoryFactory {

    private static CompteRepository repository;

    public static CompteRepository getInstance(YamlService yamlService) {
        if (repository == null) {
            RepositoryType repositoryType = yamlService.getRepositoryType("compte");
            switch (repositoryType) {
                case JPA:
                    repository = new CompteRepositoryJpa();
                    break;

                case LIST:
                    repository = new CompteRepositoryList();
                    break;

                case DATABASE:
                    repository = new CompteRepositoryDb();
                    break;

                default:
                    break;
            }
        }
        return repository;
    }

}
