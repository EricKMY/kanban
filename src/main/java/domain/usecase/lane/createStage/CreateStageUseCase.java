package domain.usecase.lane.createStage;

import domain.model.DomainEventBus;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class CreateStageUseCase {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private Workflow workflow;
    private DomainEventBus eventBus;

    public CreateStageUseCase(IWorkflowRepository workflowRepository, IBoardRepository boardRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {

        workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        String stageId;
        if (input.getParentLaneId() == null){
            stageId = workflow.createStage(input.getStageName());
        }else{
            stageId = workflow.createStage(input.getStageName(), input.getParentLaneId());
        }

        workflowRepository.save(WorkflowDTOConverter.toDTO(workflow));
        eventBus.postAll(workflow);

        output.setStageId(stageId);
    }
}
