package com.emadesko.core.factory.services;

import com.emadesko.core.factory.repository.ArticleRepositoryFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.services.ArticleService;

public abstract class ArticleServiceFactory {

    private static ArticleService service;

    public static ArticleService getInstance(YamlService yamlService) {
        if (service == null) {
            service = new ArticleService(ArticleRepositoryFactory.getInstance(yamlService));
        }
        return service;
    }

}
