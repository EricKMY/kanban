package domain.usecase.card.commitCard;

public class CommitCardInput {
    private String cardId;
    private String workflowId;
    private String laneId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardName) {
        this.cardId = cardName;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getLaneId() {
        return laneId;
    }

    public void setLaneId(String stageId) {
        this.laneId = stageId;
    }
}
