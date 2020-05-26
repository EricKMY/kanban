package domain.usecase.card.uncommitCard;

public interface UncommitCardInput {

    String getCardId();

    void setCardId(String cardName);

    String getWorkflowId();

    void setWorkflowId(String workflowId);

    String getLaneId();

    void setLaneId(String stageId);
}
