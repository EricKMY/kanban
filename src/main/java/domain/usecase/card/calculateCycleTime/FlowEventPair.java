package domain.usecase.card.calculateCycleTime;

import domain.common.DateProvider;
import domain.model.FlowEvent;

import java.util.Date;

public class FlowEventPair {
    private CycleTimeInLane cycleTimeInLane;
    public FlowEventPair(FlowEvent committed){
        String laneId = committed.getLaneId();
        Date occurredOnOfCommitted = committed.getOccurredOn();
        long diff = (DateProvider.now().getTime() - occurredOnOfCommitted.getTime()) / 1000;
        cycleTimeInLane = new CycleTimeInLane(laneId, diff);
    }
    public FlowEventPair(FlowEvent committed, FlowEvent uncommitted) {

        String laneId = committed.getLaneId();
        Date occurredOnOfCommitted = committed.getOccurredOn();
        Date occurredOnOfUncommitted = uncommitted.getOccurredOn();

        long diff = (occurredOnOfUncommitted.getTime() - occurredOnOfCommitted.getTime()) / 1000;

        cycleTimeInLane = new CycleTimeInLane(laneId, diff);
    }

    public CycleTimeInLane getCycleTimeInLane() {
        return cycleTimeInLane;
    }
}
