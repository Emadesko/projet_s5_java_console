package com.emadesko.core.factory.repository;

import com.emadesko.core.services.YamlService;
import com.emadesko.datas.enums.RepositoryType;
import com.emadesko.datas.repositories.ClientRepository;
import com.emadesko.datas.repositories.db.ClientRepositoryDb;
import com.emadesko.datas.repositories.jpa.ClientRepositoryJpa;
import com.emadesko.datas.repositories.list.ClientRepositoryList;

public abstract class ClientRepositoryFactory {

    private static ClientRepository repository;

    public static ClientRepository getInstance(YamlService yamlService) {
        if (repository == null) {
            RepositoryType repositoryType = yamlService.getRepositoryType("client");
            switch (repositoryType) {
                case JPA:
                    repository = new ClientRepositoryJpa();
                    break;

                case LIST:
                    repository = new ClientRepositoryList();
                    break;

                case DATABASE:
                    repository = new ClientRepositoryDb(CompteRepositoryFactory.getInstance(yamlService));
                    break;

                default:
                    break;
            }
        }
        return repository;
    }

}
