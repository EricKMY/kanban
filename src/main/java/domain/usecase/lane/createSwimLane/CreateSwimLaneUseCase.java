package domain.usecase.lane.createSwimLane;

import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CreateSwimLaneUseCase {
    private DomainEventBus eventBus;
    private IWorkflowRepository workflowRepository;
    private Workflow workflow;


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
}
