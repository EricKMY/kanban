package domain.adapter.repository.board;

import domain.adapter.database.table.BoardTable;
import domain.adapter.database.DatabaseConnector;
import domain.adapter.database.table.WorkflowBoardTable;
import domain.adapter.repository.board.dto.BoardRepositoryDTO;
import domain.usecase.repository.IBoardRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardInDatabaseRepository implements IBoardRepository {

    private DatabaseConnector database;

    public BoardInDatabaseRepository() {
        this.database = new DatabaseConnector();
    }

    /* refactor Board to DTO */
    public void save(BoardRepositoryDTO boardRepositoryDTO) {
        if(isBoardExist(boardRepositoryDTO.getId()))
            ;   //update
        else {
            addBoard(boardRepositoryDTO);
            addWorkflows(boardRepositoryDTO.getId(), boardRepositoryDTO.getWorkflows());
        }
    }

    /* refactor Board to DTO */
    public BoardRepositoryDTO findById(String boardId) {
        Connection connection = database.connect();
        Statement statement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT * " +
                "FROM " + BoardTable.tableName + " " +
                "WHERE " + BoardTable.id + " = '" + boardId + "'";

        BoardRepositoryDTO boardRepositoryDTO = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                boardRepositoryDTO = new BoardRepositoryDTO();
                boardRepositoryDTO.setId(resultSet.getString(BoardTable.id));
                boardRepositoryDTO.setName(resultSet.getString(BoardTable.name));
                boardRepositoryDTO.setUserId(resultSet.getString(BoardTable.userName));
//                boardDTO.setWorkflows(null);
                boardRepositoryDTO.setWorkflows(findWorkflowsByBoardId(resultSet.getString(BoardTable.id)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeResultSet(resultSet);
            database.closeStatement(statement);
            database.closeConnect(connection);
        }

        return boardRepositoryDTO;
    }

    private void addBoard(BoardRepositoryDTO boardRepositoryDTO) {
        Connection connection = database.connect();
        Statement statement = null;
        String sql =
                "INSERT INTO " + BoardTable.tableName + " " +
                        "(" + BoardTable.id + ", " +
                        BoardTable.name + ", " +
                        BoardTable.userName + ") " +
                        "VALUES (" + "'" + boardRepositoryDTO.getId() + "', " +
                        "'" + boardRepositoryDTO.getName() + "', " +
                        "'" + boardRepositoryDTO.getUserId() + "')";

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
        Connection connection = database.connect();
        Statement statement = null;
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
        Connection connection = database.connect();
        Statement statement = null;
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
        Connection connection = database.connect();
        Statement statement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT " + WorkflowBoardTable.workflowId + " " +
                "FROM " + WorkflowBoardTable.tableName + " " +
                "WHERE "+ WorkflowBoardTable.boardId + "= '" + boardId + "'";

        List<String> workflowList = new ArrayList<String>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                workflowList.add(resultSet.getString(WorkflowBoardTable.workflowId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeResultSet(resultSet);
            database.closeStatement(statement);
            database.closeConnect(connection);
        }

        return workflowList;
    }
}