package domain.usecase.card.createCard;

public interface CreateCardInput {
    String getCardName();

    void setCardName(String cardName);

    String getWorkflowId();

    void setWorkflowId(String workflowId);

    String getLaneId();

    void setLaneId(String stageId);
}
