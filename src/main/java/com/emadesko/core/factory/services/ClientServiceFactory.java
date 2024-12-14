package com.emadesko.core.factory.services;

import com.emadesko.core.factory.repository.ClientRepositoryFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.services.ClientService;

public abstract class ClientServiceFactory {

    private static ClientService service;

    public static ClientService getInstance(YamlService yamlService) {
        if (service == null) {
            service = new ClientService(ClientRepositoryFactory.getInstance(yamlService));
        }
        return service;
    }

}
