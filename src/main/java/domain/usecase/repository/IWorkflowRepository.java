package domain.usecase.repository;

import domain.model.workflow.Workflow;

public interface IWorkflowRepository {
    void save(Workflow workflow);
    Workflow findById(String id);
}

