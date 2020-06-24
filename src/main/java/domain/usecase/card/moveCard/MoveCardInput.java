package domain.usecase.card.moveCard;

public interface MoveCardInput {

    String getCardId();

    void setCardId(String cardName);

    String getWorkflowId();

    void setWorkflowId(String workflowId);

    String getOriginLaneId();

    void setOriginLaneId(String stageId);

    String getTargetLaneId();

    void setTargetLaneId(String targetLaneId);
}
