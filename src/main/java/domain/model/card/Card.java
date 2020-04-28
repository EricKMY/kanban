package domain.model.card;

import domain.model.AggregateRoot;
import domain.model.card.event.CardCreated;

public class Card extends AggregateRoot {

    private String workflowId;


    public Card(String cardName, String laneId, String workflowId) {
        super(cardName);
        addDomainEvent(new CardCreated(cardName, laneId, workflowId));
    }

    public Card(String cardName, String cardId) {
        super(cardName, cardId);
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowId(){
        return workflowId;
    }
}