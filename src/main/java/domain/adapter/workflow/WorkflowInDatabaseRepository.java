package domain.adapter.workflow;

import domain.adapter.database.WorkflowTable;
import domain.adapter.database.DatabaseConnector;
import domain.usecase.repository.IWorkflowRepository;
import domain.model.workflow.Workflow;
import domain.usecase.workflow.WorkflowDTO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkflowInDatabaseRepository implements IWorkflowRepository {

    private DatabaseConnector database;

    public WorkflowInDatabaseRepository() {
        database = new DatabaseConnector();
    }

    /* refactor Workflow to DTO */
    public void save(WorkflowDTO workflowDTO) {
        Connection connection = database.connect();
        Statement statement = null;
        String sql =
                "INSERT INTO " + WorkflowTable.tableName + " " +
                    "(" + WorkflowTable.id + ", " +
                          WorkflowTable.name + ", " +
                          WorkflowTable.boardId + ") " +
                "VALUES (" + "'" + workflowDTO.getId() + "', " +
                             "'" + workflowDTO.getName() + "', " +
                             "'" + workflowDTO.getBoardId() + "')";

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

    /* refactor Workflow to DTO */
    public WorkflowDTO findById(String workflowId){
        Connection connection = database.connect();
        Statement statement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT * " +
                "FROM " + WorkflowTable.tableName + " " +
                "WHERE workflowId = '" + workflowId + "'";

        WorkflowDTO workflowDTO = new WorkflowDTO();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                workflowDTO.setName(resultSet.getString("workflowName"));
                workflowDTO.setBoardId(resultSet.getString("boardId"));
                workflowDTO.setId(resultSet.getString("workflowId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeResultSet(resultSet);
            database.closeStatement(statement);
            database.closeConnect(connection);
        }

        return workflowDTO;
    }
}
