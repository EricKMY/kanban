package domain.usecase.repository;

import domain.usecase.workflow.WorkflowDTO;

public interface IWorkflowRepository {
    void save(WorkflowDTO workflow);
    WorkflowDTO findById(String id);
}

