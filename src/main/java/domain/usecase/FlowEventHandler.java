package domain.usecase;

import com.google.common.eventbus.Subscribe;
import domain.model.FlowEvent;
import domain.usecase.flowEvent.repository.IFlowEventRepository;

public class FlowEventHandler {
    private IFlowEventRepository IFlowEventRepository;

    public FlowEventHandler(IFlowEventRepository IFlowEventRepository) {
        this.IFlowEventRepository = IFlowEventRepository;

    }

    @Subscribe
    public void handleEvent(FlowEvent flowEvent) {
        IFlowEventRepository.save(flowEvent);
    }
}
