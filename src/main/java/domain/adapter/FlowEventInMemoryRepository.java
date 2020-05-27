package domain.adapter;

import domain.model.FlowEvent;
import domain.usecase.flowEvent.repository.IFlowEventRepository;

import java.util.ArrayList;
import java.util.List;

public class FlowEventInMemoryRepository implements IFlowEventRepository {

    List<FlowEvent> flowEvents = new ArrayList<>();

    @Override
    public void save(FlowEvent flowEvent) {
        flowEvents.add(flowEvent);
    }

    @Override
    public List<FlowEvent> getAll() {
        return flowEvents;
    }
}
