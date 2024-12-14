package com.emadesko.core.factory.repository;

import com.emadesko.core.services.YamlService;
import com.emadesko.datas.enums.RepositoryType;
import com.emadesko.datas.repositories.DetailRepository;
import com.emadesko.datas.repositories.db.DetailRepositoryDb;
import com.emadesko.datas.repositories.jpa.DetailRepositoryJpa;
import com.emadesko.datas.repositories.list.DetailRepositoryList;

public abstract class DetailRepositoryFactory {

    private static DetailRepository repository;

    public static DetailRepository getInstance(YamlService yamlService) {
        if (repository == null) {
            RepositoryType repositoryType = yamlService.getRepositoryType("detail");
            switch (repositoryType) {
                case JPA:
                    repository = new DetailRepositoryJpa();
                    break;

                case LIST:
                    repository = new DetailRepositoryList();
                    break;

                case DATABASE:
                    repository = new DetailRepositoryDb(DetteRepositoryFactory.getInstance(yamlService),ArticleRepositoryFactory.getInstance(yamlService));
                    break;

                default:
                    break;
            }
        }
        return repository;
    }

}
