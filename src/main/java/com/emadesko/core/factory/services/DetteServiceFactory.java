package com.emadesko.core.factory.services;

import com.emadesko.core.factory.repository.DetteRepositoryFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.services.DetteService;

public abstract class DetteServiceFactory {

    private static DetteService service;

    public static DetteService getInstance(YamlService yamlService) {
        if (service == null) {
            service = new DetteService(DetteRepositoryFactory.getInstance(yamlService));
        }
        return service;
    }

}
