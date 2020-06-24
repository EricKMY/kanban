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
    private String beginningStageId;
    private String endingStageId;

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
            if(entry.getValue().getId().equals(getBeginningStageId())){
                isInCycle = true;
            }else if(entry.getValue().getId().equals(getEndingStageId())){
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
    public String getBeginningStageId() {
        return beginningStageId;
    }

    @Override
    public void setBeginningStageId(String beginningStageId) {
        this.beginningStageId = beginningStageId;
    }

    @Override
    public String getEndingStageId() {
        return endingStageId;
    }

    @Override
    public void setEndingStageId(String endingStageId) {
        this.endingStageId = endingStageId;
    }
}
