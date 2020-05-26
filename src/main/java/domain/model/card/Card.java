package domain.model.card;

import domain.model.AggregateRoot;
import domain.model.card.event.CardCreated;
import domain.model.card.event.CardMoved;

public class Card extends AggregateRoot {

    private String workflowId;

    private String laneId;


    public Card(String cardName, String laneId, String workflowId) {
        super(cardName);
        this.laneId = laneId;
        this.workflowId = workflowId;
        addDomainEvent(new CardCreated(cardName, id, laneId, workflowId));
    }

    public Card(String cardName, String cardId, String laneId, String workflowId) {
        super(cardName, cardId);
        this.laneId = laneId;
        this.workflowId = workflowId;
    }

    public String getWorkflowId(){
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }

    public void moveCard(String workflowId, String targetLaneId) {
        String originalLaneId = this.laneId;
        this.laneId = targetLaneId;
        addDomainEvent(new CardMoved(workflowId, id, originalLaneId, targetLaneId));
    }
}