package com.emadesko.datas.repositories;

import java.util.List;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Article;


public interface ArticleRepository extends Repository<Article>{
    Article getArticleByLibelle(String libelle);
    List <Article> getUnavailableArticles();
}
