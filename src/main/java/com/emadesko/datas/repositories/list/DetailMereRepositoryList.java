package com.emadesko.datas.repositories.list;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryList;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.DetteMere;
import com.emadesko.datas.entities.Entite;
import com.emadesko.datas.repositories.DetailMereRepository;

public class DetailMereRepositoryList <T extends Entite> extends RepositoryList<T> implements DetailMereRepository <T> {

    @Override
    public List <T> getDetailMeresByDetteMere(DetteMere detteMere) {
        return super.select().stream().filter(detail -> detail.getDetteMere() == detteMere).toList();
    }
    
    @Override
    public List <T> getDetailMeresByArticle(Article article) {
        return super.select().stream().filter(detail -> detail.getArticle() == article).toList();
    }
}
