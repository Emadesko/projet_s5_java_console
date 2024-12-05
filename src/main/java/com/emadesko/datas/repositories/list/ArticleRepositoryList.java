package com.emadesko.datas.repositories.list;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryList;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.repositories.ArticleRepository;

public class ArticleRepositoryList extends RepositoryList<Article> implements ArticleRepository {

    @Override
    public Article getArticleByLibelle(String libelle) {
        return super.select().stream()
                .filter(Article -> Article.getLibelle().toLowerCase().compareTo(libelle.toLowerCase()) == 0).findFirst()
                .orElse(null);
    }

    @Override
    public List<Article> getUnavailableArticles() {
        return super.select().stream().filter(Article -> Article.getQteStock() == 0).toList();
    }

    @Override
    public List<Article> getAvailableArticles() {
        return super.select().stream().filter(Article -> Article.getQteStock() > 0).toList();
    }
}
