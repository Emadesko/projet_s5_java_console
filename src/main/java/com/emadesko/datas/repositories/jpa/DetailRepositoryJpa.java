package com.emadesko.datas.repositories.jpa;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryJpa;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.Detail;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.repositories.DetailRepository;

public class DetailRepositoryJpa extends RepositoryJpa<Detail> implements DetailRepository{
    
    public DetailRepositoryJpa(){
        super(Detail.class);
    }

    @Override
    public List<Detail> getDetailsByDette(Dette dette) {
        return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE dette_id = :id ", clazz)
        .setParameter("id", dette.getId())
        .getResultList();    
    }

    @Override
    public List<Detail> getDetailsByArticle(Article article) {
        return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE article_id = :id ", clazz)
        .setParameter("id", article.getId())
        .getResultList();    
    }
}
