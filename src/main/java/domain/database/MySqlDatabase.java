package domain.database;

import domain.adapter.database.BoardTable;
import domain.adapter.database.CardTable;
import domain.adapter.database.IDatabase;
import domain.adapter.database.WorkflowTable;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MySqlDatabase implements IDatabase {
    private String tableName;
    private Statement statement;
    String sql;

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

    public void createTable(String tableName) {
        this.tableName = tableName;

        if(tableName == BoardTable.name)
            createBoardTable();

        if(tableName == WorkflowTable.name)
            createWorkflowTable();

        if(tableName == CardTable.name)
            createCardTable();
    }

    public void save(String[] attribute) {
        Connection connection = this.connect();
        sql = convertToAdd(sql, attribute);

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnect(connection);
        }
    }

    public Map<String, String> findById(String id) {
        Map<String, String> result = null;

        if(this.tableName == "board")
            result = findBoardById(id);

        if(this.tableName == "workflow")
            result = findWorkflowById(id);

        if(this.tableName == "card")
            result = findCardById(id);

        return  result;
    }

    public void closeConnect(Connection connection) {
        try{
            connection.close();
        }
        catch(Exception e){
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

    private void createBoardTable() {
        Connection connection = this.connect();
        sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(" + BoardTable.id +  " VARCHAR(50) not NULL, " +
                BoardTable.name + " VARCHAR(50), " +
                BoardTable.userName +  " VARCHAR(50))";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnect(connection);
        }
    }

    private void createWorkflowTable() {
        Connection connection = this.connect();
        sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(" + WorkflowTable.id  + " VARCHAR(50) not NULL, " +
                WorkflowTable.name + " VARCHAR(50), " +
                WorkflowTable.boardId + " VARCHAR(50))";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnect(connection);
        }

    }

    private void createCardTable() {
        Connection connection = this.connect();
        sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(" + CardTable.id +  " VARCHAR(50) not NULL, " +
                CardTable.name + " VARCHAR(50), " +
                CardTable.blocker + " VARCHAR(50))";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnect(connection);
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

    private Map<String, String> findBoardById(String boardId) {
        Connection connection = this.connect();
        ResultSet resultSet = null;
        sql = "SELECT * FROM " + this.tableName + " WHERE boardId = '" + boardId + "'";
        Map<String, String> result = new HashMap<String, String>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                result.put(BoardTable.id, resultSet.getString(BoardTable.id));
                result.put(BoardTable.name, resultSet.getString(BoardTable.name));
                result.put(BoardTable.userName, resultSet.getString(BoardTable.userName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeConnect(connection);
        }
        return result;
    }

    private Map<String, String> findWorkflowById(String workflowId) {
        Connection connection = this.connect();
        ResultSet resultSet = null;
        sql = "SELECT * FROM " + this.tableName + " WHERE workflowId = '" + workflowId + "'";
        Map<String, String> result = new HashMap<String, String>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                result.put("workflowId", resultSet.getString("workflowId"));
                result.put("workflowName", resultSet.getString("workflowName"));
                result.put("boardId", resultSet.getString("boardId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeConnect(connection);
        }
        return result;
    }

    private Map<String, String> findCardById(String cardId) {
        Connection connection = this.connect();
        ResultSet resultSet = null;
        sql = "SELECT * FROM " + this.tableName + " WHERE cardId = '" + cardId + "'";
        Map<String, String> result = new HashMap<String, String>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                result.put("cardId", resultSet.getString("cardId"));
                result.put("cardName", resultSet.getString("cardName"));
                result.put("blocker", resultSet.getString("blocker"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeConnect(connection);
        }
        return result;
    }

}
