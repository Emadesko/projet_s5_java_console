package com.emadesko.datas.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.emadesko.datas.entities.Demande;
import com.emadesko.datas.entities.DetailDemande;
import com.emadesko.datas.repositories.ArticleRepository;
import com.emadesko.datas.repositories.DetailDemandeRepository;
import com.emadesko.datas.repositories.DetteMereRepository;

public class DetailDemandeRepositoryDb extends DetailMereRepositoryDb<DetailDemande, Demande> implements DetailDemandeRepository{

    public DetailDemandeRepositoryDb(DetteMereRepository<Demande> demandeRepository,ArticleRepository articleRepository){
        super(demandeRepository, articleRepository, "details_demande",DetailDemande.class,"demande_id");
    }

    
    @Override
    public DetailDemande convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        DetailDemande detailDemande = new DetailDemande();
        detailDemande.setId(rs.getInt("id"));
        detailDemande.setPrix(rs.getDouble("prix"));
        detailDemande.setQte(rs.getInt("qte"));
        detailDemande.setTotal(rs.getDouble("total"));
        detailDemande.setDemande(detteMereRepository.getById(rs.getInt(whithId)));
        detailDemande.setArticle(articleRepository.getById(rs.getInt("article_id")));
        detailDemande.setCreateAt(rs.getDate("createAt").toLocalDate());
        detailDemande.setUpdateAt(rs.getDate("updateAt").toLocalDate());
        return detailDemande;
    }

}
