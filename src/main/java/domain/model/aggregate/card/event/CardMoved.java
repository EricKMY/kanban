package domain.model.aggregate.card.event;

import domain.model.common.DateProvider;
import domain.model.DomainEvent;

import java.util.Date;

public class CardMoved implements DomainEvent {

    private String workflowId;
    private String cardId;
    private String originalLaneId;
    private String targetLaneId;
    private Date OccurredOn;

    public CardMoved(String workflowId, String cardId, String originalLaneId, String targetLaneId) {
        this.workflowId = workflowId;
        this.cardId = cardId;
        this.originalLaneId = originalLaneId;
        this.targetLaneId = targetLaneId;
        this.OccurredOn = DateProvider.now();
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getOriginalLaneId() {
        return originalLaneId;
    }

    public String getTargetLaneId() {
        return targetLaneId;
    }

    @Override
    public Date getOccurredOn() {
        return OccurredOn;
    }

    @Override
    public String getDetail() {
        return "Move card from " + originalLaneId + " to " + targetLaneId;
    }
}
