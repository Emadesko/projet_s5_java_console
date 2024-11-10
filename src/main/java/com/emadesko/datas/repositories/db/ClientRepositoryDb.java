package com.emadesko.datas.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.emadesko.core.repository.impl.RepositoryDb;
import com.emadesko.datas.entities.Client;
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
        return this.getBy("surname", surnom);
    }

    @Override
    public Client getClientByTelephone(String telephone) {
        return this.getBy("telephone", telephone);
    }

    @Override
    public String generateSql(Client client) {
        return "INSERT INTO `clients` (`adresse`, `surname`, `telephone`, `createAt`, `updateAt`, `compte_id`) VALUES (?, ?, ?, ?, ?, ?);";
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
}
