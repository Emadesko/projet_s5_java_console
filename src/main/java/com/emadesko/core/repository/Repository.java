package com.emadesko.core.repository;

import java.util.List;

public interface Repository <T>{
    void insert(T data);
    List<T> select();
    T getById(int id);
    void delete(T data);
    void update(T data);

}
