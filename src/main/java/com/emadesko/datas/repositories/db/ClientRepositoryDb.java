package com.emadesko.datas.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.emadesko.core.repository.impl.RepositoryDb;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.repositories.ClientRepository;
import com.emadesko.datas.repositories.CompteRepository;

public class ClientRepositoryDb extends RepositoryDb<Client> implements ClientRepository{

    CompteRepository compteRepository;
    public ClientRepositoryDb(CompteRepository compteRepository){
        super("clients", Client.class);
        this.compteRepository = compteRepository;
    }
    
    @Override
    public Client getClientBySurnom(String surnom) {
        return this.getBy("surname like ", surnom);
    }

    @Override
    public Client getClientByTelephone(String telephone) {
        return this.getBy("telephone like ", telephone);
    }
    
    
    @Override
    public Client getClientByCompte(Compte compte) {
        return this.getBy("compte_id = ", compte.getId());
    }

    @Override
    public String generateSql(Client client) {
        return "INSERT INTO " + this.tableName + " (`adresse`, `surname`, `telephone`, `createAt`, `updateAt`, `compte_id`) VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public void update(Client client) {
        this.getConnection();
        String sql="UPDATE  " + this.tableName + " SET `adresse` = ?, `surname` = ?, `telephone` = ?, `updateAt` = ?, `compte_id` = ?  WHERE id = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setString(1, client.getAdresse());
            this.ps.setString(2, client.getSurname());
            this.ps.setString(3, client.getTelephone());
            this.ps.setObject(4, client.getUpdateAt());
            if (client.getCompte() != null) {
                this.ps.setInt(5, client.getCompte().getId());
            }else {
                this.ps.setNull(5, java.sql.Types.NULL);
            }
            this.ps.setInt(6, client.getId());
            this.excecuteUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }

    @Override
    public void setFields(Client client) throws SQLException, IllegalAccessException {
        this.ps.setString(1, client.getAdresse());
        this.ps.setString(2, client.getSurname());
        this.ps.setString(3, client.getTelephone());
        this.ps.setObject(4, client.getCreateAt());
        this.ps.setObject(5, client.getUpdateAt());
        if (client.getCompte() != null) {
            this.ps.setInt(6, client.getCompte().getId());
        }else {
            this.ps.setNull(6, java.sql.Types.NULL);
        }
    }

    @Override
    public Client convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setAdresse(rs.getString("adresse"));
        client.setSurname(rs.getString("surname"));
        client.setTelephone(rs.getString("telephone"));
        client.setCreateAt(rs.getDate("createAt").toLocalDate());
        client.setUpdateAt(rs.getDate("updateAt").toLocalDate());
        if (rs.getObject("compte_id")!= null) {
            client.setCompte(compteRepository.getById(rs.getInt("compte_id")));
        }
        return client;
    }


    @Override
    public List<Client> getClientsByAccountStatus(boolean with) {
        List<Client> clients=new ArrayList<>();
        this.getConnection();
        String sql;
        if (with){
            sql="SELECT * FROM "+ tableName +" WHERE compte_id IS NOT NULL;";
        }else{
            sql="SELECT * FROM "+ tableName +" WHERE compte_id IS NULL;";
        }
        try {
            this.initPreparedStatment(sql);
            ResultSet rs=this.excecuteQuerry();
            while (rs.next()) {
                clients.add(convertToObject(rs));
            }
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
        return clients;
    }

}
