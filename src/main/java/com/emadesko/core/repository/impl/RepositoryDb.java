package com.emadesko.core.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.emadesko.core.repository.DataSource;
import com.emadesko.datas.entities.Entite;

public class RepositoryDb<T extends Entite> implements DataSource<T> {

    protected String tableName;
    protected Class<T> clazz;
    protected Connection connection;
    protected PreparedStatement ps;
    private String dbName="projet_java_s5";
    private String user="root";
    private String password="";
    private String url="jdbc:mysql://localhost:3306/"+ this.dbName;

    public RepositoryDb(String tableName, Class<T> clazz) {
        this.tableName = tableName;
        this.clazz = clazz;
    }

    @Override
    public void insert(T data) {
        this.getConnection();
        String sql=this.generateSql(data);
        try {
            this.initPreparedStatment(sql);
            this.setFields(data);
            this.excecuteUpdate();
            ResultSet rs=this.ps.getGeneratedKeys();
            if (rs.next()) {
                data.setId(rs.getInt(1));
            }
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }

    @Override
    public List<T> select() {
        List<T> datas=new ArrayList<>();
        this.getConnection();
        String sql="SELECT * FROM "+ tableName;
        try {
            this.initPreparedStatment(sql);
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

    
    @Override
    public T getBy(String condition, Object value) {
        T data=null;
        this.getConnection();
        String sql="SELECT * FROM " + this.tableName + " where " + condition + " ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setObject(1, value);
            ResultSet rs=this.excecuteQuerry();
            if (rs.next()) {
                data=this.convertToObject(rs);
            }
        } catch (SQLException|IllegalAccessException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
        return data;
    }

    @Override
    public T getById(int id) {
        return this.getBy("id =", id) ;
    }
    
    @Override
    public void delete(T data) {
        this.getConnection();
        String sql="DELETE * FROM " + this.tableName + " where id = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setInt(1, data.getId());
            this.excecuteUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }

    @Override
    public void update(T data) {
        this.getConnection();
        String sql="UPDATE  " + this.tableName + " SET  where id = ?";
        try {
            this.initPreparedStatment(sql);
            this.ps.setInt(1, data.getId());
            this.excecuteUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
    }

    @Override
    public void getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection=DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println("Driverrrrrrrrrrrr");
        }
             
    }

    @Override
    public void closeConnection() {
        try {
            if (this.connection!=null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generateSql(T data) {
        // Récupère la classe de l'objet
        Class<?> clazz = data.getClass();

        // Récupère tous les champs de l'objet
        Field[] fields = clazz.getDeclaredFields();

        // Construire dynamiquement la requête SQL d'INSERT
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(clazz.getSimpleName().toLowerCase()); // Le nom de la table doit correspondre au nom de la classe (en
                                                         // minuscule)
        sql.append(" (");

        StringBuilder placeholders = new StringBuilder("VALUES (");

        // Ajoute les noms de champs et les placeholders dans la requête
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            sql.append(field.getName());
            placeholders.append("?");
            if (i < fields.length - 1) {
                sql.append(", ");
                placeholders.append(", ");
            }
        }

        sql.append(") ");
        placeholders.append(")");

        // Combine la partie champs et la partie placeholders
        sql.append(placeholders.toString());

        return sql.toString();
    }

    @Override
    public void initPreparedStatment(String sql) throws SQLException {
        if (sql.trim().toUpperCase().startsWith("INSERT")) {
            this.ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            this.ps = this.connection.prepareStatement(sql);
        }
    }

    @Override
    public void setFields(T data) throws SQLException, IllegalAccessException {
        // Récupère la classe de l'objet
        Class<?> clazz = data.getClass();

        // Récupère tous les champs de l'objet
        Field[] fields = clazz.getDeclaredFields();

        // Remplir le PreparedStatement avec les valeurs des champs de l'objet
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Object value = field.get(data);
            ps.setObject(i + 1, value); // Les indices JDBC commencent à 1
        }

    }

    @Override
    public T convertToObject(ResultSet rs) throws SQLException, IllegalAccessException {
        // Crée une nouvelle instance de la classe donnée
        T object = null;
        try {
            object = this.clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Récupère tous les champs de la classe
        Field[] fields = clazz.getDeclaredFields();

        // Parcourt chaque champ de la classe
        for (Field field : fields) {
            // Rendre les champs privés accessibles
            field.setAccessible(true);
            try {
                // Récupère la valeur de la colonne correspondant au nom du champ
                Object value = rs.getObject(field.getName());

                // Assure que la valeur n'est pas nulle avant de l'assigner
                if (value != null) {
                    field.set(object, value);
                }
            } catch (SQLException e) {
                // Ignore les champs qui n'existent pas dans le ResultSet
                System.out.println("La colonne " + field.getName() + " n'existe pas dans le ResultSet");
            }
        }
        return object;
    }

    @Override
    public ResultSet excecuteQuerry()  throws SQLException{
        return this.ps.executeQuery();
    }

    @Override
    public int excecuteUpdate()  throws SQLException{
        return this.ps.executeUpdate();
    }

}
