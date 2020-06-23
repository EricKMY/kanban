package domain.adapter.database;

import domain.adapter.database.table.BoardTable;
import domain.adapter.database.table.CardTable;
import domain.adapter.database.table.WorkflowBoardTable;
import domain.adapter.database.table.WorkflowTable;

import java.sql.*;

public class DatabaseConnector {

    private String databaseName = "kanban";

    public DatabaseConnector() {
        createDatabase();
        createBoardTable();
        createWorkflowTable();
        createCardTable();
        createWorkflowBoardTable();
    }

//    User: "root", Password: "" <== Don't change, if change please notify team member.
    public Connection connect() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/" + databaseName + "?serverTimezone=UTC";
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
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
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
        Connection connection = this.connect();
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
        Connection connection = this.connect();
        Statement statement = null;
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
        Connection connection = this.connect();
        Statement statement = null;
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

    private void createWorkflowBoardTable() {
        Connection connection = this.connect();
        Statement statement = null;
        String sql = "CREATE TABLE IF NOT EXISTS " + WorkflowBoardTable.tableName +
                "(" + WorkflowBoardTable.workflowId +  " VARCHAR(50) not NULL, " +
                WorkflowBoardTable.boardId + " VARCHAR(50) not NULL)";

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
