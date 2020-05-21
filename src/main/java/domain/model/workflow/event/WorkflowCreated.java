package domain.model.workflow.event;

import domain.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class WorkflowCreated implements DomainEvent {
    private Date OccurredOn;

    private String workflowName;
    private String workflowId;
    private String boardId;

    public WorkflowCreated(String workflowName, String workflowId, String boardId){
        this.workflowName = workflowName;
        this.boardId = boardId;
        this.workflowId = workflowId;
        this.OccurredOn = DateProvider.now();
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getDetail() {
        return "Workflow Created: " + workflowName;
    }

    public String getBoardId() {
        return boardId;
    }

    public Date getOccurredOn() {
        return OccurredOn;
    }
}