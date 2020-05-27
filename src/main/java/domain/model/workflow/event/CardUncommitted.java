package domain.model.workflow.event;

import domain.model.FlowEvent;

public class CardUncommitted extends FlowEvent {

    public CardUncommitted(String workflowId, String cardId, String laneId){
        super(workflowId, cardId, laneId);
    }

    public String getDetail() {
        return "Card Uncommitted " + getCardId() + " from Lane " + getLaneId();
    }

}
