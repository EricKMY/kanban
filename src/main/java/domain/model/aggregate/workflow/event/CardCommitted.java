package domain.model.aggregate.workflow.event;

import domain.model.FlowEvent;

public class CardCommitted extends FlowEvent {

    public CardCommitted(String workflowId, String cardId, String laneId){
        super(workflowId, cardId, laneId);
    }

    public String getDetail() {
        return "Card Committed " + getCardId() + " to Lane " + getLaneId();
    }
}
