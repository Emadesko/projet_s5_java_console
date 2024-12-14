package com.emadesko.core.services;

import java.util.Map;

import com.emadesko.datas.enums.RepositoryType;

public interface YamlService {
    Map<String, Object> load();
    Map<String, Object> load(String path);
    RepositoryType getRepositoryType(String entite);
}
