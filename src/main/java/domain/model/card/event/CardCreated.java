package domain.model.card.event;

import domain.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class CardCreated implements DomainEvent {
    private Date OccurredOn;
    private String cardName;
    private String workflowId;
    private String laneId;

    public CardCreated(String cardName, String laneId, String workflowId){
        this.cardName = cardName;
        this.laneId = laneId;
        this.workflowId = workflowId;
        this.OccurredOn = DateProvider.now();
    }

    public String getCardName() {
        return cardName;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public Date getOccurredOn() {
        return OccurredOn;
    }

    public String getLaneId() {
        return laneId;
    }
}