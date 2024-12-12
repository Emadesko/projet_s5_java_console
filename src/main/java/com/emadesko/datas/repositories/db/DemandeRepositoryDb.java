package com.emadesko.datas.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Demande;
import com.emadesko.datas.enums.Etat;
import com.emadesko.datas.repositories.ClientRepository;
import com.emadesko.datas.repositories.DemandeRepository;

public class DemandeRepositoryDb extends DetteMereRepositoryDb<Demande> implements DemandeRepository{

    ClientRepository clientRepository;

    public DemandeRepositoryDb(ClientRepository clientRepository){
        super("demandes", Demande.class);
        this.clientRepository = clientRepository;
    }

    @Override
    public String generateSql(Demande demande) {
        return "INSERT INTO " + this.tableName + " (`createAt`, `etat`, `montant`, `updateAt`, `client_id`) VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public void update(Demande demande) {
        this.getConnection();
        String sql="UPDATE  " + this.tableName + " SET `createAt` = ?, `etat` = ?, `montant` = ?, `updateAt` = ? , `client_id` = ?  WHERE id = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setObject(1, demande.getCreateAt());
            this.ps.setInt(2, demande.getEtat().ordinal());
            this.ps.setDouble(3, demande.getMontant());
            this.ps.setObject(4, demande.getUpdateAt());
            this.ps.setInt(5, demande.getClient().getId());
            this.ps.setInt(6, demande.getId());
            this.excecuteUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }

    @Override
    public void setFields(Demande demande) throws SQLException, IllegalAccessException {
        this.ps.setObject(1, demande.getCreateAt());
            this.ps.setInt(2, demande.getEtat().ordinal());
            this.ps.setDouble(3, demande.getMontant());
            this.ps.setObject(4, demande.getUpdateAt());
            this.ps.setInt(5, demande.getClient().getId());
    }

    @Override
    public Demande convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        Demande demande = new Demande();
        demande.setId(rs.getInt("id"));
        demande.setCreateAt(rs.getDate("createAt").toLocalDate());
        demande.setUpdateAt(rs.getDate("updateAt").toLocalDate());
        demande.setMontant(rs.getDouble("montant"));
        demande.setClient(clientRepository.getById(rs.getInt("client_id")));
        demande.setEtat(Etat.values()[rs.getInt("etat")]);
        return demande;
    }

    @Override
    public List<Demande> getDemandesByEtat(Etat etat) {
        return getManyBy("etat = ", etat.ordinal());
    }

    @Override
    public List<Demande> getDemandesByEtatAndClient(Etat etat, Client client) {
        List<Demande> datas=new ArrayList<>();
        this.getConnection();
        String sql="SELECT * FROM "+ tableName + " WHERE client_id = ? and etat = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setInt(1, client.getId());
            this.ps.setInt(2, etat.ordinal());
            ResultSet rs=this.excecuteQuerry();
            while (rs.next()) {
                datas.add(convertToObject(rs));
            }
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
        return datas;
    }
}
