package domain.adapter.database;

import domain.database.Database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MySQL implements Database {
    private String tableName;
    private Statement statement;
    String sql;

    public Connection connect() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/kanban";
        String user = "root";
        String password = "";

        Connection connection = null;
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public void createTable(String tableName) {
        this.tableName = tableName;

        if(tableName == "workflow")
            createWorkflowTable();
    }

    public void save(String[] attribute) {
        sql = convertToAdd(sql, attribute);
        Connection connection = this.connect();

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, String> findById(String id) {
        Connection connection = this.connect();
        ResultSet resultSet = null;
        sql = "SELECT * FROM " + this.tableName + " WHERE id = '" + id + "'";
        Map<String, String> result = new HashMap<String, String>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                result.put("id", resultSet.getString("id"));
                result.put("name", resultSet.getString("name"));
                result.put("boardId", resultSet.getString("boardId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  result;
    }

    private void createWorkflowTable() {
        Connection connection = this.connect();
        sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(id VARCHAR(50) not NULL, name VARCHAR(50), boardId VARCHAR(50))";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private String convertToAdd(String sql, String[] attribute) {
        sql = "INSERT INTO " + this.tableName + " VALUES (";
        for(int i=0; i<attribute.length; i++) {
            sql = sql + "'" + attribute[i] + "'" ;

            if(i!=(attribute.length-1))
                sql = sql + ", ";
            else
                sql = sql + ")";
        }
        return sql;
    }

}
