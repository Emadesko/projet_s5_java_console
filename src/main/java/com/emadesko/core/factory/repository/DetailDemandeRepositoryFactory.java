package com.emadesko.core.factory.repository;

import com.emadesko.core.services.YamlService;
import com.emadesko.datas.enums.RepositoryType;
import com.emadesko.datas.repositories.DetailDemandeRepository;
import com.emadesko.datas.repositories.db.DetailDemandeRepositoryDb;
import com.emadesko.datas.repositories.jpa.DetailDemandeRepositoryJpa;
import com.emadesko.datas.repositories.list.DetailDemandeRepositoryList;

public abstract class DetailDemandeRepositoryFactory {

    private static DetailDemandeRepository repository;

    public static DetailDemandeRepository getInstance(YamlService yamlService) {
        if (repository == null) {
            RepositoryType repositoryType = yamlService.getRepositoryType("detailDemande");
            switch (repositoryType) {
                case JPA:
                    repository = new DetailDemandeRepositoryJpa();
                    break;

                case LIST:
                    repository = new DetailDemandeRepositoryList();
                    break;

                case DATABASE:
                    repository = new DetailDemandeRepositoryDb(DemandeRepositoryFactory.getInstance(yamlService),ArticleRepositoryFactory.getInstance(yamlService));
                    break;

                default:
                    break;
            }
        }
        return repository;
    }

}
