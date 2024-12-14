package com.emadesko.core.factory.services;

import com.emadesko.core.factory.repository.DemandeRepositoryFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.services.DemandeService;

public abstract class DemandeServiceFactory {

    private static DemandeService service;

    public static DemandeService getInstance(YamlService yamlService) {
        if (service == null) {
            service = new DemandeService(DemandeRepositoryFactory.getInstance(yamlService));
        }
        return service;
    }

}
