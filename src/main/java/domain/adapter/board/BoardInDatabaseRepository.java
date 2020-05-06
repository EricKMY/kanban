package domain.adapter.board;

import domain.adapter.database.BoardTable;
import domain.adapter.database.DatabaseConnector;
import domain.adapter.database.WorkflowBoardTable;
import domain.model.board.Board;
import domain.usecase.board.BoardDTO;
import domain.usecase.repository.IBoardRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardInDatabaseRepository implements IBoardRepository {

    private DatabaseConnector database;
    private Connection connection = null;
    private Statement statement = null;

    public BoardInDatabaseRepository() {
        this.database = new DatabaseConnector();
    }

    /* refactor Board to DTO */
    public void save(BoardDTO boardDTO) {
        if(isBoardExist(boardDTO.getId()))
            ;   //update
        else {
            addBoard(boardDTO);
            addWorkflows(boardDTO.getId(), boardDTO.getWorkflows());
        }
    }

    /* refactor Board to DTO */
    public BoardDTO findById(String boardId) {
        connection = database.connect();
        ResultSet resultSet = null;
        String sql =
                "SELECT * " +
                "FROM " + BoardTable.tableName + " " +
                "WHERE " + BoardTable.id + " = '" + boardId + "'";

        BoardDTO boardDTO = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                boardDTO = new BoardDTO();
                boardDTO.setId(resultSet.getString(BoardTable.id));
                boardDTO.setName(resultSet.getString(BoardTable.name));
                boardDTO.setUsername(resultSet.getString(BoardTable.userName));
//                boardDTO.setWorkflows(null);
                boardDTO.setWorkflows(findWorkflowsByBoardId(resultSet.getString(BoardTable.id)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeResultSet(resultSet);
            database.closeStatement(statement);
            database.closeConnect(connection);
        }

        return boardDTO;
    }

    private void addBoard(BoardDTO boardDTO) {
        connection = database.connect();
        statement = null;
        String sql =
                "INSERT INTO " + BoardTable.tableName + " " +
                        "(" + BoardTable.id + ", " +
                        BoardTable.name + ", " +
                        BoardTable.userName + ") " +
                        "VALUES (" + "'" + boardDTO.getId() + "', " +
                        "'" + boardDTO.getName() + "', " +
                        "'" + boardDTO.getUsername() + "')";

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
                "WHERE " + BoardTable.id + " = '" + boardId + "'";

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

    private List<String> findWorkflowsByBoardId(String boardId) {
        connection = database.connect();
        ResultSet resultSet = null;
        String sql =
                "SELECT " + WorkflowBoardTable.workflowId + " " +
                "FROM " + WorkflowBoardTable.tableName + " " +
                "WHERE "+ WorkflowBoardTable.boardId + "= '" + boardId + "'";

        List<String> workflows = new ArrayList<String>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                workflows.add(resultSet.getString(WorkflowBoardTable.workflowId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeResultSet(resultSet);
            database.closeStatement(statement);
            database.closeConnect(connection);
        }

        return workflows;
    }
}