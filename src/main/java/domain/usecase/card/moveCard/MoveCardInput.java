package domain.usecase.card.moveCard;

public interface MoveCardInput {

    String getCardId();

    void setCardId(String cardName);

    String getWorkflowId();

    void setWorkflowId(String workflowId);

    String getLaneId();

    void setLaneId(String stageId);

    String getTargetLaneId();

    void setTargetLaneId(String targetLaneId);
}
