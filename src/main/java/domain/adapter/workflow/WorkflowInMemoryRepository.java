package domain.adapter.workflow;

import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTO;

import java.util.HashMap;
import java.util.Map;

public class WorkflowInMemoryRepository implements IWorkflowRepository {
    Map<String, WorkflowDTO> workflowMap = new HashMap<String, WorkflowDTO>();

    public void save(WorkflowDTO workflowDTO) {
        workflowMap.put(workflowDTO.getId(), workflowDTO);
    }

    public WorkflowDTO findById(String workflowId) {
        return workflowMap.get(workflowId);
    }

}
