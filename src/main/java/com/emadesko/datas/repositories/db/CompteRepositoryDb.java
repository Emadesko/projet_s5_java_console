package com.emadesko.datas.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.emadesko.core.repository.impl.RepositoryDb;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.enums.Role;
import com.emadesko.datas.repositories.CompteRepository;

public class CompteRepositoryDb extends RepositoryDb<Compte> implements CompteRepository{

    public CompteRepositoryDb(){
        super("comptes", Compte.class);
    }

    @Override
    public Compte getCompteByLogin(String login) {
        return this.getBy("login LIKE ", login);
    }

    @Override
    public Compte getCompteByEmail(String email) {
        return this.getBy("email LIKE ", email);
    }

    @Override
    public String generateSql(Compte compte) {
        return "INSERT INTO " + this.tableName + " (`email`, `login`, `nom`, `prenom`, `password`, `role`, `createAt`,  `updateAt`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public void setFields(Compte compte) throws SQLException, IllegalAccessException {
        this.ps.setString(1, compte.getEmail());
        this.ps.setString(2, compte.getLogin());
        this.ps.setString(3, compte.getNom());
        this.ps.setString(4, compte.getPrenom());
        this.ps.setString(5, compte.getPassword());
        this.ps.setInt(6, compte.getRole().ordinal());
        this.ps.setObject(7, compte.getUpdateAt());
        this.ps.setObject(8, compte.getUpdateAt());
    }

    @Override
    public Compte convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        Compte compte = new Compte();
        compte.setId(rs.getInt("id"));
        compte.setEmail(rs.getString("email"));
        compte.setLogin(rs.getString("login"));
        compte.setNom(rs.getString("nom"));
        compte.setPrenom(rs.getString("prenom"));
        compte.setPassword(rs.getString("password"));
        compte.setRole(Role.values()[rs.getInt("role")]);
        compte.setCreateAt(rs.getDate("createAt").toLocalDate());
        compte.setUpdateAt(rs.getDate("updateAt").toLocalDate());
        return compte;
    }

}
