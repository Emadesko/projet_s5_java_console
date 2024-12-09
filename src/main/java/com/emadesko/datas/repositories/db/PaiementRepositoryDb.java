package com.emadesko.datas.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.emadesko.core.repository.impl.RepositoryDb;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.entities.Paiement;
import com.emadesko.datas.repositories.PaiementRepository;
import com.emadesko.datas.repositories.DetteRepository;

public class PaiementRepositoryDb extends RepositoryDb<Paiement> implements PaiementRepository{

    DetteRepository detteRepository;
    public PaiementRepositoryDb(DetteRepository detteRepository){
        super("paiements", Paiement.class);
        this.detteRepository = detteRepository;
    }

    @Override
    public String generateSql(Paiement paiement) {
        return "INSERT INTO " + this.tableName + " (`dette_id`, `montant`, `createAt`, `updateAt`) VALUES (?, ?, ?, ?);";
    }

    @Override
    public void update(Paiement paiement) {
        this.getConnection();
        String sql="UPDATE  " + this.tableName + " SET `dette_id` = ?, `montant` = ?, `createAt` = ?, `updateAt` = ? WHERE id = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setInt(1, paiement.getDette().getId());
            this.ps.setDouble(2, paiement.getMontant());
            this.ps.setObject(3, paiement.getCreateAt());
            this.ps.setObject(4, paiement.getUpdateAt());
            this.ps.setInt(5, paiement.getId());
            this.excecuteUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }

    @Override
    public void setFields(Paiement paiement) throws SQLException, IllegalAccessException {
        this.ps.setInt(1, paiement.getDette().getId());
        this.ps.setDouble(2, paiement.getMontant());
        this.ps.setObject(3, paiement.getCreateAt());
        this.ps.setObject(4, paiement.getUpdateAt());
    }

    @Override
    public Paiement convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        Paiement paiement = new Paiement();
        paiement.setId(rs.getInt("id"));
        paiement.setMontant(rs.getDouble("montant"));
        paiement.setDette(detteRepository.getById(rs.getInt("dette_id")));
        paiement.setCreateAt(rs.getDate("createAt").toLocalDate());
        paiement.setUpdateAt(rs.getDate("updateAt").toLocalDate());
        return paiement;
    }


    @Override
    public List<Paiement> getPaiementsByDette(Dette dette) {
        return super.getManyBy("dette_id = ",dette.getId());
    }

}
