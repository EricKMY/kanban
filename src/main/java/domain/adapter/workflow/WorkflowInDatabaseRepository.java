package domain.adapter.workflow;

import domain.adapter.database.BoardTable;
import domain.adapter.database.WorkflowTable;
import domain.adapter.database.DatabaseConnector;
import domain.usecase.repository.IWorkflowRepository;
import domain.model.workflow.Workflow;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkflowInDatabaseRepository implements IWorkflowRepository {

    private DatabaseConnector database;
    private Connection connection = null;
    private Statement statement = null;

    public WorkflowInDatabaseRepository() {
        database = new DatabaseConnector();
    }

    /* refactor Workflow to DTO */
    public void save(Workflow workflow) {
        connection = database.connect();
        statement = null;
        String sql =
                "INSERT INTO " + WorkflowTable.tableName + " " +
                    "(" + WorkflowTable.id + ", " +
                          WorkflowTable.name + ", " +
                          WorkflowTable.boardId + ") " +
                "VALUES (" + "'" + workflow.getId() + "', " +
                             "'" + workflow.getName() + "', " +
                             "'" + workflow.getBoardId() + "')";

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
    public Workflow findById(String workflowId){
        connection = database.connect();
        ResultSet resultSet = null;
        String sql =
                "SELECT * " +
                "FROM " + WorkflowTable.tableName + " " +
                "WHERE workflowId = '" + workflowId + "'";

        Workflow workflow = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                workflow = new Workflow(
                        resultSet.getString("workflowName"),
                        resultSet.getString("boardId"),
                        resultSet.getString("workflowId")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeResultSet(resultSet);
            database.closeStatement(statement);
            database.closeConnect(connection);
        }

        return workflow;
    }
}
