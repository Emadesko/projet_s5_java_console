package com.emadesko.core.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DataSource<T> extends Repository<T>{
    void getConnection();
    void closeConnection();
    String generateSql(T data);
    void initPreparedStatment(String sql) throws SQLException;
    void setFields(T data) throws SQLException, IllegalAccessException;
    T convertToObject(ResultSet rs)throws SQLException, IllegalAccessException;
    ResultSet excecuteQuerry() throws SQLException;
    int excecuteUpdate() throws SQLException;
    T getBy(String condition, Object value);
    List<T> getManyBy(String condition, Object value);
}
