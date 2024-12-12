package com.emadesko.datas.repositories.jpa;

import com.emadesko.datas.entities.Detail;
import com.emadesko.datas.repositories.DetailRepository;

public class DetailRepositoryJpa extends DetailMereRepositoryJpa<Detail> implements DetailRepository{
    
    public DetailRepositoryJpa(){
        super(Detail.class,"dette_id");
    }
}
