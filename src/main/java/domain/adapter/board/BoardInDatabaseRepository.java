package domain.adapter.board;

import domain.adapter.database.BoardTable;
import domain.adapter.database.DatabaseConnector;
import domain.adapter.database.WorkflowBoardTable;
import domain.model.board.Board;
import domain.usecase.repository.IBoardRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BoardInDatabaseRepository implements IBoardRepository {

    private DatabaseConnector database;
    private Connection connection = null;
    private Statement statement = null;

    public BoardInDatabaseRepository() {
        this.database = new DatabaseConnector();
    }

    /* refactor Board to DTO */
    public void save(Board board) {
        if(isBoardExist(board.getId()))
            ;   //update
        else {
            addBoard(board);
            addWorkflows(board.getId(), board.getWorkflows());
        }
    }

    /* refactor Board to DTO */
    public Board findById(String boardId) {
        connection = database.connect();
        ResultSet resultSet = null;
        String sql =
                "SELECT * " +
                "FROM " + BoardTable.tableName + " " +
                "WHERE boardId = '" + boardId + "'";

        Board board = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                board = new Board(
                        resultSet.getString(BoardTable.name),
                        resultSet.getString(BoardTable.userName),
                        resultSet.getString(BoardTable.id)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeResultSet(resultSet);
            database.closeStatement(statement);
            database.closeConnect(connection);
        }

        return board;
    }

    private void addBoard(Board board) {
        connection = database.connect();
        statement = null;
        String sql =
                "INSERT INTO " + BoardTable.tableName + " " +
                        "(" + BoardTable.id + ", " +
                        BoardTable.name + ", " +
                        BoardTable.userName + ") " +
                        "VALUES (" + "'" + board.getId() + "', " +
                        "'" + board.getName() + "', " +
                        "'" + board.getUsername() + "')";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeStatement(statement);
            database.closeConnect(connection);
        }
    }

    private boolean isBoardExist(String boardId) {
        connection = database.connect();
        statement = null;
        ResultSet resultSet = null;
        boolean isBoardExist = false;
        String sql =
                "SELECT " + BoardTable.name + " " +
                "FROM " + BoardTable.tableName + " " +
                "WHERE boardId = '" + boardId + "'";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if(resultSet.next())
                isBoardExist = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeStatement(statement);
            database.closeConnect(connection);
        }
        return isBoardExist;
    }

    private void addWorkflows(String boardId, List<String> workflowIds) {
        for(String workflowId : workflowIds) {
            addWorkflow(boardId, workflowId);
        }

    }

    private void addWorkflow(String boardId, String workflowId) {
        connection = database.connect();
        statement = null;
        String sql =
                "INSERT INTO " + WorkflowBoardTable.tableName + " " +
                        "(" + WorkflowBoardTable.workflowId + ", "
                            + WorkflowBoardTable.boardId + ") " +
                "VALUES (" + "'" + workflowId + "', "
                           + "'" + boardId + "')";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeStatement(statement);
            database.closeConnect(connection);
        }
    }
}