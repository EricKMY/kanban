package domain.adapter.card.calculateCycleTime;

import domain.usecase.card.calculateCycleTime.CalculateCycleTimeOutput;
import domain.usecase.card.cycleTime.CycleTime;

public class CalculateCycleTimePresenter implements CalculateCycleTimeOutput {
    private CycleTime cycleTime;

    @Override
    public void setCycleTime(CycleTime cycleTime) {
        this.cycleTime = cycleTime;
    }

    @Override
    public CycleTime getCycleTime() {
        return cycleTime;
    }
}
