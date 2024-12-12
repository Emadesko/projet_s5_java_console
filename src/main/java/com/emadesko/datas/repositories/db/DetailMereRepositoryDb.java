package com.emadesko.datas.repositories.db;

import java.sql.SQLException;
import java.util.List;

import com.emadesko.core.repository.impl.RepositoryDb;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.DetailMere;
import com.emadesko.datas.entities.DetteMere;
import com.emadesko.datas.repositories.DetteMereRepository;
import com.emadesko.datas.repositories.ArticleRepository;
import com.emadesko.datas.repositories.DetailMereRepository;

public class DetailMereRepositoryDb <T extends DetailMere , D extends DetteMere>  extends RepositoryDb<T> implements DetailMereRepository<T>{

    protected String whithId;
    protected DetteMereRepository<D> detteMereRepository;
    protected ArticleRepository articleRepository;    

    public DetailMereRepositoryDb(DetteMereRepository<D> detteMereRepository,ArticleRepository articleRepository, String tableName, Class<T> clazz, String whithId){
        super(tableName, clazz);
        this.detteMereRepository = detteMereRepository;
        this.articleRepository = articleRepository;
        this.whithId = whithId;
    }

    @Override
    public String generateSql(T detail) {

        return "INSERT INTO " + this.tableName + " (`prix`, `qte`, `createAt`, `updateAt`, `article_id`, `" + whithId + "`, `total`) VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public void update(T detail) {
        this.getConnection();
        String sql="UPDATE  " + this.tableName + " SET `prix` = ?, `qte` = ?, `createAt` = ?, `updateAt` = ?, `article_id` = ?, `" + whithId + "` = ?, `tottal` = ? WHERE id = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setDouble(1, detail.getPrix());
            this.ps.setInt(2, detail.getQte());
            this.ps.setObject(3, detail.getCreateAt());
            this.ps.setObject(4, detail.getUpdateAt());
            this.ps.setInt(5, detail.getArticle().getId());
            this.ps.setInt(6, detail.getDetteMere().getId());
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
    public void setFields(T detail) throws SQLException, IllegalAccessException {
        this.ps.setDouble(1, detail.getPrix());
        this.ps.setInt(2, detail.getQte());
        this.ps.setObject(3, detail.getCreateAt());
        this.ps.setObject(4, detail.getUpdateAt());
        this.ps.setInt(5, detail.getArticle().getId());
        this.ps.setInt(6, detail.getDetteMere().getId());
        this.ps.setDouble(7, detail.getTotal());
    }

    @Override
    public List<T> getDetailMeresByDetteMere(DetteMere detteMere) {
        return super.getManyBy(whithId + " = ",detteMere.getId());
    }
    
    @Override
    public List<T> getDetailMeresByArticle(Article article) {
        return super.getManyBy("article_id = ",article.getId());
    }

}
