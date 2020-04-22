package domain.usecase.repository;

import domain.model.workflow.Workflow;

import java.sql.Connection;

public interface IWorkflowRepository {
    void save(Workflow workflow);
    Workflow findById(String id);
}

