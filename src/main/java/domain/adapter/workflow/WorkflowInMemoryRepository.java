package domain.adapter.workflow;

import domain.model.board.Board;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

import java.util.HashMap;
import java.util.Map;

public class WorkflowInMemoryRepository implements IWorkflowRepository {
    Map<String, Workflow> map = new HashMap<String, Workflow>();

    public void save(Workflow workflow) {
        map.put(workflow.getWorkflowId(), workflow);
    }

    public Workflow findById(String workflowId) {
        return map.get(workflowId);
    }

}
