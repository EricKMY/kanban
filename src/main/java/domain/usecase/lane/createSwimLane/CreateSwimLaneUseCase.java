package domain.usecase.lane.createSwimLane;

import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CreateSwimLaneUseCase implements CreateSwimLaneInput{
    private DomainEventBus eventBus;
    private IWorkflowRepository workflowRepository;
    private Workflow workflow;
    private String swimLaneName;
    private String workflowId;
    private String parentLaneId;

    public CreateSwimLaneUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateSwimLaneInput input, CreateSwimLaneOutput output) {
        workflow = WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        String swimLaneId = workflow.createSwimLane(input.getSwimLaneName(), input.getParentLaneId());

        workflowRepository.save(WorkflowRepositoryDTOConverter.toDTO(workflow));
        eventBus.postAll(workflow);

        output.setSwimLaneId(swimLaneId);
    }

    public String getSwimLaneName() {
        return swimLaneName;
    }

    public void setSwimLaneName(String swimLaneName) {
        this.swimLaneName = swimLaneName;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getParentLaneId() {
        return parentLaneId;
    }

    public void setParentLaneId(String parentLaneId) {
        this.parentLaneId = parentLaneId;
    }
}
