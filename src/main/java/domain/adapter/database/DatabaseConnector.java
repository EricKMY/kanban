package domain.adapter.database;

import java.sql.*;

public class DatabaseConnector {

    private String databaseName = "kanban";
    private Connection connection = null;
    private Statement statement = null;

    public DatabaseConnector() {
        createDatabase();
        createBoardTable();
        createWorkflowTable();
        createCardTable();
    }

//    User: "root", Password: "" <== Don't change, if change notify.
    public Connection connect() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/kanban?serverTimezone=UTC";
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

    public void closeConnect(Connection connection) {
        try{
            connection.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void closeStatement(Statement statement) {
        try {
            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        try{
            resultSet.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void createDatabase() {
        Connection connection = this.connect();
        Statement statement = null;
        String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createBoardTable() {
        connection = this.connect();
        Statement statement = null;
        String sql = "CREATE TABLE IF NOT EXISTS " + BoardTable.tableName +
                "(" + BoardTable.id +  " VARCHAR(50) not NULL, " +
                      BoardTable.name + " VARCHAR(50), " +
                      BoardTable.userName +  " VARCHAR(50))";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeConnect(connection);
        }
    }

    private void createWorkflowTable() {
        connection = this.connect();
        String sql = "CREATE TABLE IF NOT EXISTS " + WorkflowTable.tableName +
                "(" + WorkflowTable.id  + " VARCHAR(50) not NULL, " +
                      WorkflowTable.name + " VARCHAR(50), " +
                      WorkflowTable.boardId + " VARCHAR(50))";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeConnect(connection);
        }
    }

    private void createCardTable() {
        connection = this.connect();
        String sql = "CREATE TABLE IF NOT EXISTS " + CardTable.tableName +
                "(" + CardTable.id  + " VARCHAR(50) not NULL, " +
                      CardTable.name + " VARCHAR(50), " +
                      CardTable.blocker + " VARCHAR(50))";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeConnect(connection);
        }
    }
}
