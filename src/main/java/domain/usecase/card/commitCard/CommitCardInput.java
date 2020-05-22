package domain.usecase.card.commitCard;

public interface CommitCardInput {

    String getCardId();

    void setCardId(String cardName);

    String getWorkflowId();

    void setWorkflowId(String workflowId);

    String getLaneId();

    void setLaneId(String stageId);
}
