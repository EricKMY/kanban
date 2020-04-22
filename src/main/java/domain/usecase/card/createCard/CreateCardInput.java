package domain.usecase.card.createCard;

public class CreateCardInput {
    private String cardName;
    private String workflowId;
    private String laneId;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
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
