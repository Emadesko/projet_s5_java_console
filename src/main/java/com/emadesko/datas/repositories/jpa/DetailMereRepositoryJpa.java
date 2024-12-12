package com.emadesko.datas.repositories.jpa;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryJpa;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.DetteMere;
import com.emadesko.datas.entities.Entite;
import com.emadesko.datas.repositories.DetailMereRepository;

public class DetailMereRepositoryJpa <T extends Entite> extends RepositoryJpa<T> implements DetailMereRepository<T>{
    
    private String whithId;
    
    public DetailMereRepositoryJpa(Class<T> clazz, String whithId){
        super(clazz);
        this.whithId = whithId;
    }
    
    @Override
    public List<T> getDetailMeresByDetteMere(DetteMere detteMere) {
        return super.selectManyBy(whithId + " = :id", "id", detteMere.getId());
    }

    @Override
    public List<T> getDetailMeresByArticle(Article article) {
        return super.selectManyBy("article_id = :id", "id", article.getId());
    }

}
