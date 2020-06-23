package domain.usecase.repository;

import domain.adapter.repository.workflow.dto.WorkflowRepositoryDTO;

public interface IWorkflowRepository {
    void save(WorkflowRepositoryDTO workflow);
    WorkflowRepositoryDTO findById(String id);
}

