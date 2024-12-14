package com.emadesko.core.factory.services;

import com.emadesko.core.factory.repository.DetailDemandeRepositoryFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.services.DetailDemandeService;

public abstract class DetailDemandeServiceFactory {

    private static DetailDemandeService service;

    public static DetailDemandeService getInstance(YamlService yamlService) {
        if (service == null) {
            service = new DetailDemandeService(DetailDemandeRepositoryFactory.getInstance(yamlService));
        }
        return service;
    }

}
