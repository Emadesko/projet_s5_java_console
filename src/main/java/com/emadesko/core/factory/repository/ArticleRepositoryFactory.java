package com.emadesko.core.factory.repository;

import com.emadesko.core.services.YamlService;
import com.emadesko.datas.enums.RepositoryType;
import com.emadesko.datas.repositories.ArticleRepository;
import com.emadesko.datas.repositories.db.ArticleRepositoryDb;
import com.emadesko.datas.repositories.jpa.ArticleRepositoryJpa;
import com.emadesko.datas.repositories.list.ArticleRepositoryList;

public abstract class ArticleRepositoryFactory {

    private static ArticleRepository repository;

    public static ArticleRepository getInstance(YamlService yamlService) {
        if (repository == null) {
            RepositoryType repositoryType = yamlService.getRepositoryType("article");
            switch (repositoryType) {
                case JPA:
                    repository = new ArticleRepositoryJpa();
                    break;

                case LIST:
                    repository = new ArticleRepositoryList();
                    break;

                case DATABASE:
                    repository = new ArticleRepositoryDb();
                    break;

                default:
                    break;
            }
        }
        return repository;
    }

}
