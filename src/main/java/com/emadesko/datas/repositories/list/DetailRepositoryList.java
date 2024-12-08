package com.emadesko.datas.repositories.list;

import java.util.List;

import com.emadesko.core.repository.impl.RepositoryList;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.Detail;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.repositories.DetailRepository;

public class DetailRepositoryList extends RepositoryList<Detail> implements DetailRepository {

    @Override
    public List<Detail> getDetailsByDette(Dette dette) {
        return super.select().stream().filter(detail -> detail.getDette() == dette).toList();
    }

    @Override
    public List<Detail> getDetailsByArticle(Article article) {
        return super.select().stream().filter(detail -> detail.getArticle() == article).toList();
    }
}
