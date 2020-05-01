package domain.adapter.board;

import domain.adapter.database.BoardTable;
import domain.adapter.database.DatabaseConnector;
import domain.model.board.Board;
import domain.usecase.repository.IBoardRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardInDatabaseRepository implements IBoardRepository {

    private DatabaseConnector database;
    private Connection connection = null;
    private Statement statement = null;

    public BoardInDatabaseRepository() {
        this.database = new DatabaseConnector();
    }

    /* refactor Board to DTO */
    public void save(Board board) {
        connection = database.connect();
        statement = null;
        String sql = "INSERT INTO " + BoardTable.tableName + " " +
                     "VALUES(" + "'" + board.getId() + "', " +
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

    /* refactor Board to DTO */
    public Board findById(String boardId) {
        connection = database.connect();
        ResultSet resultSet = null;
        String sql = "SELECT * " +
                     "FROM " + BoardTable.tableName + " " +
                     "WHERE boardId = '" + boardId + "'";

        Board board = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                board = new Board(
                        resultSet.getString(BoardTable.id),
                        resultSet.getString(BoardTable.name),
                        resultSet.getString(BoardTable.userName)
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
}