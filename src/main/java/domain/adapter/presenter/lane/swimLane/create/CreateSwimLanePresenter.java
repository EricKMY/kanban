package domain.adapter.presenter.lane.swimLane.create;

import domain.usecase.lane.createSwimLane.CreateSwimLaneOutput;

public class CreateSwimLanePresenter implements CreateSwimLaneOutput {
    private String swimLaneId;

    public String getSwimLaneId() {
        return swimLaneId;
    }

    public void setSwimLaneId(String swimLaneId) {
        this.swimLaneId = swimLaneId;
    }
}
