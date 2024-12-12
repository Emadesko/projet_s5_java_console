package com.emadesko.datas.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.repositories.ClientRepository;
import com.emadesko.datas.repositories.DetteRepository;

public class DetteRepositoryDb extends DetteMereRepositoryDb<Dette> implements DetteRepository{

    private ClientRepository clientRepository;

    public DetteRepositoryDb(ClientRepository clientRepository){
        super("dettes", Dette.class);
        this.clientRepository = clientRepository;
    }

    @Override
    public String generateSql(Dette Dette) {
        return "INSERT INTO " + this.tableName + " (`createAt`, `isSolde`, `montant`, `montantVerser`, `updateAt`, `client_id`) VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public void update(Dette dette) {
        this.getConnection();
        String sql="UPDATE  " + this.tableName + " SET `createAt` = ?, `isSolde` = ?, `montant` = ?, `montantVerser` = ?, `updateAt` = ? , `client_id` = ?  WHERE id = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setObject(1, dette.getCreateAt());
            this.ps.setBoolean(2, dette.isSolde());
            this.ps.setDouble(3, dette.getMontant());
            this.ps.setDouble(4, dette.getMontantVerser());
            this.ps.setObject(5, dette.getUpdateAt());
            this.ps.setInt(6, dette.getClient().getId());
            this.ps.setInt(7, dette.getId());
            this.excecuteUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }

    @Override
    public void setFields(Dette dette) throws SQLException, IllegalAccessException {
        this.ps.setObject(1, dette.getCreateAt());
        this.ps.setBoolean(2, dette.isSolde());
        this.ps.setDouble(3, dette.getMontant());
        this.ps.setDouble(4, dette.getMontantVerser());
        this.ps.setObject(5, dette.getUpdateAt());
        this.ps.setInt(6, dette.getClient().getId());
    }

    @Override
    public Dette convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        Dette dette = new Dette();
        dette.setId(rs.getInt("id"));
        dette.setCreateAt(rs.getDate("createAt").toLocalDate());
        dette.setUpdateAt(rs.getDate("updateAt").toLocalDate());
        dette.setMontant(rs.getDouble("montant"));
        dette.setMontantVerser(rs.getDouble("montantVerser"));
        dette.setSolde(rs.getBoolean("isSolde"));
        dette.setClient(clientRepository.getById(rs.getInt("client_id")));
        return dette;
    }

    @Override
    public List<Dette> getDettesNonSoldesByClient(Client client) {
        List<Dette> datas=new ArrayList<>();
        this.getConnection();
        String sql="SELECT * FROM "+ tableName + " WHERE client_id = ? and isSolde = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setInt(1, client.getId());
            this.ps.setBoolean(2, false);
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
