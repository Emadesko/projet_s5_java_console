package com.emadesko.services;

import java.util.List;


public interface Service <T>{

    void create(T data);

    List<T> getAll();

    T getById(int id);

    T getById(List<T> datas, int id);

    void delete(T data);

    void update(T data);

}
