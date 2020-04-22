package domain.database;

import domain.adapter.database.Database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MySQL implements Database {
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

        if(tableName == "board")
            createBoardTable();

        if(tableName == "workflow")
            createWorkflowTable();

        if(tableName == "card")
            createCardTable();
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
        Map<String, String> result = null;

        if(this.tableName == "board")
            result = findBoardById(id);

        if(this.tableName == "workflow")
            result = findWorkflowById(id);

        if(this.tableName == "card")
            result = findCardById(id);

        return  result;
    }


    private void createBoardTable() {
        Connection connection = this.connect();
        sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(boardId VARCHAR(50) not NULL, boardName VARCHAR(50), userName VARCHAR(50))";

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

    private void createWorkflowTable() {
        Connection connection = this.connect();
        sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(workflowId VARCHAR(50) not NULL, workflowName VARCHAR(50), boardId VARCHAR(50))";

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

    private void createCardTable() {
        Connection connection = this.connect();
        sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(cardId VARCHAR(50) not NULL, cardName VARCHAR(50), blocker VARCHAR(50))";

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

    private Map<String, String> findBoardById(String boardId) {
        Connection connection = this.connect();
        ResultSet resultSet = null;
        sql = "SELECT * FROM " + this.tableName + " WHERE boardId = '" + boardId + "'";
        Map<String, String> result = new HashMap<String, String>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                result.put("boardId", resultSet.getString("boardId"));
                result.put("boardName", resultSet.getString("boardName"));
                result.put("userName", resultSet.getString("userName"));
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
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
