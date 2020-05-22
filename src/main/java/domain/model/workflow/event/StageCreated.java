package domain.model.workflow.event;

import domain.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class StageCreated implements DomainEvent {
    private Date OccurredOn;

    private String stageName;
    private String parentId;
    private String stageId;

    public StageCreated(String stageName, String stageId) {
        this.stageName = stageName;
        this.stageId = stageId;
        this.OccurredOn = DateProvider.now();
    }

    public StageCreated(String stageName, String stageId, String parentId) {
        this.stageName = stageName;
        this.stageId = stageId;
        this.parentId = parentId;
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

    public String getDetail() {
        return "Stage Created: " + stageName;
    }
}
