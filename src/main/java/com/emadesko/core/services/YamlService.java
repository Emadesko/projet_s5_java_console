package com.emadesko.core.services;

import java.util.Map;

public interface YamlService {
    Map<String, Object> load();
    Map<String, Object> load(String path);
}
