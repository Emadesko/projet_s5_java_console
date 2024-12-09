package com.emadesko.datas.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.emadesko.core.repository.impl.RepositoryDb;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.Detail;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.repositories.DetailRepository;
import com.emadesko.datas.repositories.DetteRepository;
import com.emadesko.datas.repositories.ArticleRepository;

public class DetailRepositoryDb extends RepositoryDb<Detail> implements DetailRepository{

    DetteRepository detteRepository;
    ArticleRepository articleRepository;
    public DetailRepositoryDb(DetteRepository detteRepository,ArticleRepository articleRepository){
        super("details", Detail.class);
        this.detteRepository = detteRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public String generateSql(Detail detail) {
        return "INSERT INTO " + this.tableName + " (`prix`, `qte`, `createAt`, `updateAt`, `article_id`, `dette_id`, `total`) VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public void update(Detail detail) {
        this.getConnection();
        String sql="UPDATE  " + this.tableName + " SET `prix` = ?, `qte` = ?, `createAt` = ?, `updateAt` = ?, `article_id` = ?, `dette_id` = ?, `tottal` = ? WHERE id = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setDouble(1, detail.getPrix());
            this.ps.setInt(2, detail.getQte());
            this.ps.setObject(3, detail.getCreateAt());
            this.ps.setObject(4, detail.getUpdateAt());
            this.ps.setInt(5, detail.getArticle().getId());
            this.ps.setInt(6, detail.getDette().getId());
            this.ps.setDouble(7, detail.getTotal());
            this.ps.setInt(8, detail.getId());
            this.excecuteUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }

    @Override
    public void setFields(Detail detail) throws SQLException, IllegalAccessException {
        this.ps.setDouble(1, detail.getPrix());
        this.ps.setInt(2, detail.getQte());
        this.ps.setObject(3, detail.getCreateAt());
        this.ps.setObject(4, detail.getUpdateAt());
        this.ps.setInt(5, detail.getArticle().getId());
        this.ps.setInt(6, detail.getDette().getId());
        this.ps.setDouble(7, detail.getTotal());
    }

    @Override
    public Detail convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        Detail detail = new Detail();
        detail.setId(rs.getInt("id"));
        detail.setPrix(rs.getDouble("prix"));
        detail.setQte(rs.getInt("qte"));
        detail.setDette(detteRepository.getById(rs.getInt("dette_id")));
        detail.setArticle(articleRepository.getById(rs.getInt("article_id")));
        detail.setCreateAt(rs.getDate("createAt").toLocalDate());
        detail.setUpdateAt(rs.getDate("updateAt").toLocalDate());
        return detail;
    }

    @Override
    public List<Detail> getDetailsByDette(Dette dette) {
        return super.getManyBy("dette_id = ",dette.getId());
    }

    @Override
    public List<Detail> getDetailsByArticle(Article article) {
        return super.getManyBy("article_id = ",article.getId());
    }

}
