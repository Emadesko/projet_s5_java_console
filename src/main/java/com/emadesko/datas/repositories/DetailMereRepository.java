package com.emadesko.datas.repositories;

import java.util.List;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.DetteMere;


public interface DetailMereRepository<T> extends Repository<T>{
    List <T> getDetailMeresByDetteMere(DetteMere detteMere);
    List <T> getDetailMeresByArticle(Article article);
}