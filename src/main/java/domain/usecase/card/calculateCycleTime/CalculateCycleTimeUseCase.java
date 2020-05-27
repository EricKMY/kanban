package domain.usecase.card.calculateCycleTime;

import domain.model.FlowEvent;
import domain.model.workflow.Lane;
import domain.model.workflow.Workflow;
import domain.usecase.card.cycleTime.CycleTime;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class CalculateCycleTimeUseCase implements CalculateCycleTimeInput {
    private String workflowId;
    private String cardId;
    private String beginningLaneId;
    private String endingLaneId;

    private IWorkflowRepository workflowRepository;
    private IFlowEventRepository flowEventRepository;
    private List<FlowEventPair> flowEventPairs;

    public CalculateCycleTimeUseCase(IWorkflowRepository workflowRepository, IFlowEventRepository flowEventRepository) {
        this.workflowRepository = workflowRepository;
        this.flowEventRepository = flowEventRepository;
        flowEventPairs = new ArrayList<>();
    }

    public void execute(CalculateCycleTimeInput input, CalculateCycleTimeOutput output) {
        Stack<FlowEvent> stack = new Stack<>();

        for(FlowEvent flowEvent: flowEventRepository.getAll()) {
            if(!flowEvent.getCardId().equals(input.getCardId())) {
                continue;
            }
            if(stack.empty()) {
                stack.push(flowEvent);
            }else {
                FlowEvent committed = stack.pop();
                flowEventPairs.add(new FlowEventPair(committed, flowEvent));
            }
        }

        if(!stack.empty()){
            flowEventPairs.add(new FlowEventPair(stack.pop()));
        }

        Workflow workflow = WorkflowDTOConverter.toEntity(
                workflowRepository.findById(input.getWorkflowId()));
        boolean isInCycle = false;
        List<String> laneIds = new ArrayList();
        for(Map.Entry<String, Lane> entry: workflow.getLaneMap().entrySet()){
            if(entry.getValue().getId().equals(input.getBeginningLaneId())){
                isInCycle = true;
            }else if(entry.getValue().getId().equals(input.getEndingLaneId())){
                laneIds.add(entry.getValue().getId());
                break;
            }
            if(isInCycle) {
                laneIds.add(entry.getValue().getId());
            }

        }

        long time = 0;
        for(String laneId: laneIds){
            for(FlowEventPair flowEventPair: flowEventPairs){
                if(flowEventPair.getCycleTimeInLane().getLaneId().equals(laneId)){
                    time += flowEventPair.getCycleTimeInLane().getDiff();
                }
            }
        }

        CycleTime cycleTime = new CycleTime(time);

        output.setCycleTime(cycleTime);

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
