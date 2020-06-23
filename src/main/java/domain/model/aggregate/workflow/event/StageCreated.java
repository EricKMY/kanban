package domain.model.aggregate.workflow.event;

import domain.model.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class StageCreated implements DomainEvent {
    private Date OccurredOn;

    private String stageName;
    private String parentId;
    private String stageId;
    private String workflowId;

    public StageCreated(String stageName, String stageId, String workflowId) {
        this.stageName = stageName;
        this.stageId = stageId;
        this.workflowId = workflowId;
        this.OccurredOn = DateProvider.now();
    }

    public StageCreated(String stageName, String stageId, String parentId, String workflowId) {
        this.stageName = stageName;
        this.stageId = stageId;
        this.parentId = parentId;
        this.workflowId = workflowId;
        this.OccurredOn = DateProvider.now();
    }

    @Override
    public Date getOccurredOn() {
        return OccurredOn;
    }

    public String getStageId() {
        return stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public String getParentId() {
        return parentId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getDetail() {
        return "Stage Created: " + stageName;
    }
}
