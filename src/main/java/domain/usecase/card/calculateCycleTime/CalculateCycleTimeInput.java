package domain.usecase.card.calculateCycleTime;

public interface CalculateCycleTimeInput {
    String getWorkflowId();

    void setWorkflowId(String workflowId);

    String getCardId();

    void setCardId(String cardId);

    String getBeginningStageId();

    void setBeginningStageId(String beginningStageId);

    String getEndingStageId();

    void setEndingStageId(String endingStageId);
}
