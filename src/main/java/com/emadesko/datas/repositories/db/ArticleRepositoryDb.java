package com.emadesko.datas.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.emadesko.core.repository.impl.RepositoryDb;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.repositories.ArticleRepository;

public class ArticleRepositoryDb extends RepositoryDb<Article> implements ArticleRepository{

    public ArticleRepositoryDb(){
        super("articles", Article.class);
    }
    
    @Override
    public Article getArticleByLibelle(String libelle) {
        return this.getBy("libelle like ", libelle);
    }

    @Override
    public String generateSql(Article article) {
        return "INSERT INTO `articles` (`libelle`, `reference`, `prix`, `createAt`, `updateAt`, `qteStock`) VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public void update(Article article) {
        this.getConnection();
        String sql="UPDATE  " + this.tableName + " SET `libelle` = ?, `reference` = ?, `prix` = ?, `updateAt` = ?, `qteStock` = ?  WHERE id = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setString(1, article.getLibelle());
            this.ps.setString(2, article.getReference());
            this.ps.setDouble(3, article.getPrix());
            this.ps.setObject(4, article.getUpdateAt());
            this.ps.setInt(5, article.getQteStock());
            this.ps.setInt(6, article.getId());
            this.excecuteUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }

    @Override
    public void setFields(Article article) throws SQLException, IllegalAccessException {
        this.ps.setString(1, article.getLibelle());
        this.ps.setString(2, article.getReference());
        this.ps.setDouble(3, article.getPrix());
        this.ps.setObject(4, article.getCreateAt());
        this.ps.setObject(5, article.getUpdateAt());
        this.ps.setInt(6, article.getQteStock());
        
    }

    @Override
    public Article convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setLibelle(rs.getString("libelle"));
        article.setReference(rs.getString("reference"));
        article.setPrix(rs.getDouble("prix"));
        article.setCreateAt(rs.getDate("createAt").toLocalDate());
        article.setUpdateAt(rs.getDate("updateAt").toLocalDate());
        article.setQteStock(rs.getInt("qteStock"));
        return article;
    }

    @Override
    public List<Article> getUnavailableArticles() {
        List<Article> unavailableArticles=new ArrayList<>();
        this.getConnection();
        String sql="SELECT * FROM "+ tableName +" WHERE qteStock == 0;";
        try {
            this.initPreparedStatment(sql);
            ResultSet rs=this.excecuteQuerry();
            while (rs.next()) {
                unavailableArticles.add(convertToObject(rs));
            }
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
        return unavailableArticles;
    }
}
