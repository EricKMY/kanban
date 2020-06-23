package domain.adapter.presenter.lane.stage.create;

import domain.usecase.lane.createStage.CreateStageOutput;

public class CreateStagePresenter implements CreateStageOutput {
    private String stageId;

    @Override
    public String getStageId() {
        return stageId;
    }

    @Override
    public void setStageId(String stageId) {
        this.stageId = stageId;
    }
}
