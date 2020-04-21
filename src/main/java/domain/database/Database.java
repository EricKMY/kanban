package domain.database;

import java.sql.Connection;
import java.util.Map;

public interface Database {
    Connection connect();
    void createTable(String tableName);
    void save(String[] attribute);
    Map<String, String> findById(String id);
}
