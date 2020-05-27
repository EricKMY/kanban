package domain.usecase.card.calculateCycleTime;

public interface CalculateCycleTimeInput {
    String getWorkflowId();

    void setWorkflowId(String workflowId);

    String getCardId();

    void setCardId(String cardId);

    String getBeginningLaneId();

    void setBeginningLaneId(String beginningLaneId);

    String getEndingLaneId();

    void setEndingLaneId(String endingLaneId);
}
