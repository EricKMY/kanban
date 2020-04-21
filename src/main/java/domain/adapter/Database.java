package domain.adapter;

import java.sql.Connection;
import java.sql.ResultSet;

public interface Database {
    Connection connect();
    void createTable(String tableName);
    void save(String[] attribute);
//    ResultSet findById(String id);
}
