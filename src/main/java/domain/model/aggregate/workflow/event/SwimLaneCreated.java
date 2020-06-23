package domain.model.aggregate.workflow.event;

import domain.model.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class SwimLaneCreated implements DomainEvent {
    private Date OccurredOn;

    private String swimLaneName;
    private String parentId;
    private String stageId;
    private String workflowId;

    public SwimLaneCreated(String swimLaneName, String stageId, String workflowId) {
        this.swimLaneName = swimLaneName;
        this.stageId = stageId;
        this.workflowId = workflowId;
        this.OccurredOn = DateProvider.now();
    }

    public SwimLaneCreated(String swimLaneName, String stageId, String parentId, String workflowId) {
        this.swimLaneName = swimLaneName;
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

    public String getSwimLaneName() {
        return swimLaneName;
    }

    public String getParentId() {
        return parentId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getDetail() {
        return "Swimlane Created: " + swimLaneName;
    }
}
