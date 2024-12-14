package com.emadesko.core.services.impl;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.emadesko.core.services.YamlService;
import com.emadesko.datas.enums.RepositoryType;

public class YamlServiceImpl implements YamlService{

    private String path = "application.yaml";

    @Override
    public Map<String, Object> load() {
        return this.load(this.path);
    }

    @Override
    public Map<String, Object> load(String path) {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        return yaml.load(inputStream);
    }

    @Override
    public RepositoryType getRepositoryType(String entite) {
        Map<String, Object> map = this.load();
        Map<String, Object> repo = ( Map<String, Object>) map.get("repository");
        return RepositoryType.valueOf(repo.get(entite).toString().toUpperCase());
    }
    
}
