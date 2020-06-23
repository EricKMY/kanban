package domain.adapter.presenter.card.cycleTime;

import domain.usecase.card.calculateCycleTime.CalculateCycleTimeOutput;
import domain.usecase.card.calculateCycleTime.CycleTimeModel;

public class CalculateCycleTimePresenter implements CalculateCycleTimeOutput {
    private CycleTimeModel cycleTimeModel;

    @Override
    public void setCycleTimeModel(CycleTimeModel cycleTimeModel) {
        this.cycleTimeModel = cycleTimeModel;
    }

    @Override
    public CycleTimeModel getCycleTimeModel() {
        return cycleTimeModel;
    }
}
