package com.emadesko.datas.repositories;

import java.util.List;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.Detail;
import com.emadesko.datas.entities.Dette;


public interface DetailRepository extends Repository<Detail>{
    List <Detail> getDetailsByDette(Dette dette);
    List <Detail> getDetailsByArticle(Article article);
}
