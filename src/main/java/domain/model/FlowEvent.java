package domain.model;

import domain.model.common.DateProvider;

import java.util.Date;

public class FlowEvent implements DomainEvent {
    private Date occurredOn;

    private String workflowId;
    private String laneId;
    private String cardId;

    public FlowEvent(String workflowId, String cardId, String laneId) {
        this.workflowId = workflowId;
        this.laneId = laneId;
        this.cardId = cardId;
        this.occurredOn = DateProvider.now();
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String getDetail() {
        return "Card is moving.";
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }

    public String getCardId() {
        return cardId;
    }
}
