package domain.model.workflow.event;

import domain.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class SwimlaneCreated implements DomainEvent {
    private Date OccurredOn;

    private String swimlaneName;
    private String parentId;
    private String stageId;

    public SwimlaneCreated(String swimlaneName, String stageId) {
        this.swimlaneName = swimlaneName;
        this.stageId = stageId;
        this.OccurredOn = DateProvider.now();
    }

    public SwimlaneCreated(String swimlaneName, String stageId, String parentId) {
        this.swimlaneName = swimlaneName;
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

    public String getSwimlaneName() {
        return swimlaneName;
    }

    public String getParentId() {
        return parentId;
    }

    public String getDetail() {
        return "Swimlane Created: " + swimlaneName;
    }
}
