package com.emadesko.core.factory.services;

import com.emadesko.core.factory.repository.DetailRepositoryFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.services.DetailService;

public abstract class DetailServiceFactory {

    private static DetailService service;

    public static DetailService getInstance(YamlService yamlService) {
        if (service == null) {
            service = new DetailService(DetailRepositoryFactory.getInstance(yamlService));
        }
        return service;
    }

}
