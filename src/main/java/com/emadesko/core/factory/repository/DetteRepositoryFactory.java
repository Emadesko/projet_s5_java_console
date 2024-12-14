package com.emadesko.core.factory.repository;

import com.emadesko.core.services.YamlService;
import com.emadesko.datas.enums.RepositoryType;
import com.emadesko.datas.repositories.DetteRepository;
import com.emadesko.datas.repositories.db.DetteRepositoryDb;
import com.emadesko.datas.repositories.jpa.DetteRepositoryJpa;
import com.emadesko.datas.repositories.list.DetteRepositoryList;

public abstract class DetteRepositoryFactory{

    private static DetteRepository repository;

    public static DetteRepository getInstance(YamlService yamlService) {
        if (repository==null) {
            RepositoryType repositoryType = yamlService.getRepositoryType("dette");
            switch (repositoryType) {
                case JPA:
                    repository = new DetteRepositoryJpa();
                    break;
            
                case LIST:
                    repository = new DetteRepositoryList();
                    break;
            
                case DATABASE:
                    repository = new DetteRepositoryDb(ClientRepositoryFactory.getInstance(yamlService));
                    break;
            
                default:
                    break;
            }
        }
        return repository;
    }
    
}
