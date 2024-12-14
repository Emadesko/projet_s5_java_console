package com.emadesko.core.factory.repository;

import com.emadesko.core.services.YamlService;
import com.emadesko.datas.enums.RepositoryType;
import com.emadesko.datas.repositories.PaiementRepository;
import com.emadesko.datas.repositories.db.PaiementRepositoryDb;
import com.emadesko.datas.repositories.jpa.PaiementRepositoryJpa;
import com.emadesko.datas.repositories.list.PaiementRepositoryList;

public abstract class PaiementRepositoryFactory {

    private static PaiementRepository repository;

    public static PaiementRepository getInstance(YamlService yamlService) {
        if (repository == null) {
            RepositoryType repositoryType = yamlService.getRepositoryType("paiement");
            switch (repositoryType) {
                case JPA:
                    repository = new PaiementRepositoryJpa();
                    break;

                case LIST:
                    repository = new PaiementRepositoryList();
                    break;

                case DATABASE:
                    repository = new PaiementRepositoryDb(DetteRepositoryFactory.getInstance(yamlService));
                    break;

                default:
                    break;
            }
        }
        return repository;
    }

}
