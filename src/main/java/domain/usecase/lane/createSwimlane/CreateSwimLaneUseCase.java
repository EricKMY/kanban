package domain.usecase.lane.createSwimLane;

import domain.model.workflow.Workflow;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class CreateSwimLaneUseCase {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private Workflow workflow;


    public CreateSwimLaneUseCase(IWorkflowRepository workflowRepository, IBoardRepository boardRepository) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
    }

    public void execute(CreateSwimLaneInput input, CreateSwimLaneOutput output) {
        workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        String swimLaneId = workflow.createSwimLane(input.getSwimLaneName(), input.getParentLaneId());

        workflowRepository.save(WorkflowDTOConverter.toDTO(workflow));

        output.setSwimLaneId(swimLaneId);
    }
}
