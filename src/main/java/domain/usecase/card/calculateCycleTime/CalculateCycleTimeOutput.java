package domain.usecase.card.calculateCycleTime;

import domain.usecase.card.cycleTime.CycleTime;

public interface CalculateCycleTimeOutput {
    void setCycleTime(CycleTime cycleTime);
    CycleTime getCycleTime();
}
