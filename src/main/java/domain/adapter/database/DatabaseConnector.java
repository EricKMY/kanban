package domain.adapter.database;

import java.sql.*;

public class DatabaseConnector {

    private String databaseName = "kanban";

    public DatabaseConnector() {
        createDatabase();
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
}
