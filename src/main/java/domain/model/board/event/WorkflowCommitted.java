package domain.model.board.event;

import domain.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class WorkflowCommitted implements DomainEvent {
    private Date OccurredOn;

    private String boardId;
    private String workflowId;

    public WorkflowCommitted(String boardId, String workflowId){
        this.boardId = boardId;
        this.workflowId = workflowId;
        this.OccurredOn = DateProvider.now();
    }


    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getDetail() {
        return "Workflow Committed " + workflowId + " to Board " + boardId;
    }


    public Date getOccurredOn() {
        return OccurredOn;
    }
}
