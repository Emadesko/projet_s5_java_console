package com.emadesko.core.factory.services;

import com.emadesko.core.factory.repository.CompteRepositoryFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.services.CompteService;

public abstract class CompteServiceFactory {

    private static CompteService service;

    public static CompteService getInstance(YamlService yamlService) {
        if (service == null) {
            service = new CompteService(CompteRepositoryFactory.getInstance(yamlService));
        }
        return service;
    }

}
