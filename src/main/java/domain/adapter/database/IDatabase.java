package domain.adapter.database;

import java.sql.Connection;
import java.util.Map;

public interface IDatabase {
    Connection connect();
    void createTable(String tableName);
    void save(String[] attribute);
    Map<String, String> findById(String id);
    void closeConnect(Connection connection);
}
