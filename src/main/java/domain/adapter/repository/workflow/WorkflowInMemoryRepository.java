package domain.adapter.repository.workflow;

import domain.adapter.repository.workflow.dto.WorkflowRepositoryDTO;
import domain.usecase.repository.IWorkflowRepository;

import java.util.HashMap;
import java.util.Map;

public class WorkflowInMemoryRepository implements IWorkflowRepository {
    Map<String, WorkflowRepositoryDTO> workflowMap = new HashMap<String, WorkflowRepositoryDTO>();

    public void save(WorkflowRepositoryDTO workflowRepositoryDTO) {
        workflowMap.put(workflowRepositoryDTO.getId(), workflowRepositoryDTO);
    }

    public WorkflowRepositoryDTO findById(String workflowId) {
        return workflowMap.get(workflowId);
    }

}
