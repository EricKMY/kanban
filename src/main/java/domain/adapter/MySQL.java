package domain.adapter;

import java.sql.*;

public class MySQL implements Database {
    private String tableName;
    private Statement statement;
    private ResultSet resultSet = null;
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

//    public ResultSet findById(String id) {
//        sql = "SELECT * FROME " + this.tableName + " WHERE id = '" + id + "'";
//        Connection connection = this.connect();
//
//        try {
//            statement = connection.createStatement();
//            statement.executeUpdate(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void createWorkflowTable() {
        Connection connection = this.connect();
        sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(id VARCHAR(50) not NULL, name VARCHAR(10))";

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
