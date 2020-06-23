package domain.usecase.card.calculateCycleTime;

import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.FlowEvent;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.model.service.cycleTime.CycleTimeCalculator;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IWorkflowRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculateCycleTimeUseCase implements CalculateCycleTimeInput {
    private String workflowId;
    private String cardId;
    private String beginningLaneId;
    private String endingLaneId;

    private IWorkflowRepository workflowRepository;
    private IFlowEventRepository flowEventRepository;
    private DomainEventBus domainEventBus;

    public CalculateCycleTimeUseCase(IWorkflowRepository workflowRepository, IFlowEventRepository flowEventRepository, DomainEventBus domainEventBus) {
        this.workflowRepository = workflowRepository;
        this.flowEventRepository = flowEventRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CalculateCycleTimeInput input, CalculateCycleTimeOutput output) {
        List<String> cycleLaneIds = getCycleLaneIds();

        List<FlowEvent> flowEvents = flowEventRepository.getAll();

        CycleTimeCalculator cycleTimeCalculator =
                new CycleTimeCalculator(
                        cycleLaneIds,
                        flowEvents,
                        input.getCardId());

        CycleTimeModel cycleTimeModel = new CycleTimeModel(cycleTimeCalculator.process());

        domainEventBus.postAll(cycleTimeCalculator);
        output.setCycleTimeModel(cycleTimeModel);
    }

    private List<String> getCycleLaneIds() {
        List<String> laneIds = new ArrayList();
        Workflow workflow = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId));
        boolean isInCycle = false;

        for(Map.Entry<String, Lane> entry: workflow.getLaneMap().entrySet()){
            if(entry.getValue().getId().equals(getBeginningLaneId())){
                isInCycle = true;
            }else if(entry.getValue().getId().equals(getEndingLaneId())){
                laneIds.add(entry.getValue().getId());
                break;
            }
            if(isInCycle) {
                laneIds.add(entry.getValue().getId());
            }
        }
        return laneIds;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String getCardId() {
        return cardId;
    }

    @Override
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public String getBeginningLaneId() {
        return beginningLaneId;
    }

    @Override
    public void setBeginningLaneId(String beginningLaneId) {
        this.beginningLaneId = beginningLaneId;
    }

    @Override
    public String getEndingLaneId() {
        return endingLaneId;
    }

    @Override
    public void setEndingLaneId(String endingLaneId) {
        this.endingLaneId = endingLaneId;
    }
}
