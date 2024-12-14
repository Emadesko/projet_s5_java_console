package com.emadesko.datas.repositories.db;

import com.emadesko.datas.entities.Dette;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.emadesko.datas.entities.Detail;
import com.emadesko.datas.repositories.ArticleRepository;
import com.emadesko.datas.repositories.DetailRepository;
import com.emadesko.datas.repositories.DetteMereRepository;

public class DetailRepositoryDb extends DetailMereRepositoryDb<Detail, Dette> implements DetailRepository{

    public DetailRepositoryDb(DetteMereRepository<Dette> detteRepository,ArticleRepository articleRepository){
        super(detteRepository, articleRepository, "details",Detail.class,"dette_id");
    }

    
    @Override
    public Detail convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        Detail detail = new Detail();
        detail.setId(rs.getInt("id"));
        detail.setPrix(rs.getDouble("prix"));
        detail.setQte(rs.getInt("qte"));
        detail.setTotal(rs.getDouble("total"));
        detail.setDette(detteMereRepository.getById(rs.getInt(whithId)));
        detail.setArticle(articleRepository.getById(rs.getInt("article_id")));
        detail.setCreateAt(rs.getDate("createAt").toLocalDate());
        detail.setUpdateAt(rs.getDate("updateAt").toLocalDate());
        System.out.println(detail.getArticle());
        return detail;
    }

    
}
