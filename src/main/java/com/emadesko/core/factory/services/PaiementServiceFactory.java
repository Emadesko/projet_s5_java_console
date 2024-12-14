package com.emadesko.core.factory.services;

import com.emadesko.core.factory.repository.PaiementRepositoryFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.services.PaiementService;

public abstract class PaiementServiceFactory {

    private static PaiementService service;

    public static PaiementService getInstance(YamlService yamlService) {
        if (service == null) {
            service = new PaiementService(PaiementRepositoryFactory.getInstance(yamlService));
        }
        return service;
    }

}
