package com.emadesko.services;

import java.util.List;


public interface Service <T>{

    public void create(T data);

    public List<T> getAll();
}
