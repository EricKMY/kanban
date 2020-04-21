package domain.usecase;

import domain.model.workflow.Workflow;

import java.sql.Connection;

public interface Repository {
    Connection getConnection();
    void add(Workflow item);
//    Workflow findById(String id);
}

