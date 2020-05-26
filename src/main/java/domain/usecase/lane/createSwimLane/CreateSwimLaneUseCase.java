package domain.usecase.lane.createSwimLane;

import domain.model.DomainEventBus;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class CreateSwimLaneUseCase {
    private DomainEventBus eventBus;
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private Workflow workflow;


    public CreateSwimLaneUseCase(IWorkflowRepository workflowRepository, IBoardRepository boardRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateSwimLaneInput input, CreateSwimLaneOutput output) {
        workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        String swimLaneId = workflow.createSwimLane(input.getSwimLaneName(), input.getParentLaneId());

        workflowRepository.save(WorkflowDTOConverter.toDTO(workflow));
        eventBus.postAll(workflow);

        output.setSwimLaneId(swimLaneId);
    }
}
