package domain.model.workflow.event;

import domain.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class CardUncommitted implements DomainEvent {

    private Date OccurredOn;
    private String workflowId;
    private String cardId;
    private String laneId;

    public CardUncommitted(String workflowId, String cardId, String laneId){
        this.workflowId = workflowId;
        this.cardId = cardId;
        this.laneId = laneId;
        this.OccurredOn = DateProvider.now();
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getLaneId() {
        return laneId;
    }

    public String getDetail() {
        return "Card Uncommitted " + cardId + " from Lane " + laneId;
    }

    public Date getOccurredOn() {
        return OccurredOn;
    }
}
