package domain.usecase.flowEvent.repository;

import domain.model.FlowEvent;

import java.util.List;

public interface IFlowEventRepository {
    void save(FlowEvent flowEvent);

    List<FlowEvent> getAll();
}
