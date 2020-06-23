package domain.model.aggregate.card.event;

import domain.model.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class CardCreated implements DomainEvent {
    private Date OccurredOn;
    private String cardName;
    private String workflowId;
    private String laneId;
    private String cardId;

    public CardCreated(String cardName, String cardId, String laneId, String workflowId){
        this.cardName = cardName;
        this.cardId = cardId;
        this.laneId = laneId;
        this.workflowId = workflowId;
        this.OccurredOn = DateProvider.now();
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardId() {
        return cardId;
    }

    public String getDetail() {
        return "Card Created: " + cardName;
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