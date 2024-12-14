package com.emadesko.core.factory.repository;

import com.emadesko.core.services.YamlService;
import com.emadesko.datas.enums.RepositoryType;
import com.emadesko.datas.repositories.DemandeRepository;
import com.emadesko.datas.repositories.db.DemandeRepositoryDb;
import com.emadesko.datas.repositories.jpa.DemandeRepositoryJpa;
import com.emadesko.datas.repositories.list.DemandeRepositoryList;

public abstract class DemandeRepositoryFactory {

    private static DemandeRepository repository;

    public static DemandeRepository getInstance(YamlService yamlService) {
        if (repository == null) {
            RepositoryType repositoryType = yamlService.getRepositoryType("demande");
            switch (repositoryType) {
                case JPA:
                    repository = new DemandeRepositoryJpa();
                    break;

                case LIST:
                    repository = new DemandeRepositoryList();
                    break;

                case DATABASE:
                    repository = new DemandeRepositoryDb(ClientRepositoryFactory.getInstance(yamlService));
                    break;

                default:
                    break;
            }
        }
        return repository;
    }

}
