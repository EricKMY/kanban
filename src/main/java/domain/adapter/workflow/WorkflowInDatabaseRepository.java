package domain.adapter.workflow;

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
        createWorkflowTable();
    }

    public void save(Workflow workflow) {
        connection = database.connect();
        statement = null;
        String sql = "INSERT INTO " + WorkflowTable.tableName + " " +
                     "VALUES(" + "'" + workflow.getId() + "', " +
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

    public Workflow findById(String workflowId){
        connection = database.connect();
        ResultSet resultSet = null;
        String sql = "SELECT * " +
                     "FROM " + WorkflowTable.tableName + " " +
                     "WHERE workflowId = '" + workflowId + "'";

        Workflow workflow = null;

        /* refactor Workflow to DTO */
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                workflow = new Workflow(
                        resultSet.getString("workflowId"),
                        resultSet.getString("workflowName"),
                        resultSet.getString("boardId")
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

    private void createWorkflowTable() {
        connection = database.connect();
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
            database.closeStatement(statement);
            database.closeConnect(connection);
        }
    }
}
