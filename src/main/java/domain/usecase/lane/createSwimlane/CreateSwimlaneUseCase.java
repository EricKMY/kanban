package domain.usecase.lane.createSwimlane;

import domain.model.workflow.Workflow;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;

public class CreateSwimlaneUseCase {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private Workflow workflow;


    public CreateSwimlaneUseCase(IWorkflowRepository workflowRepository, IBoardRepository boardRepository) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
    }

    public void execute(CreateSwimlaneInput input, CreateSwimlaneOutput output) {
        workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(input.getWorkflowId()));
        String swinlaneId = workflow.createSwimlane(input.getSwimlaneName(), input.getParentLaneId());

        workflowRepository.save(WorkflowDTOConverter.toDTO(workflow));

        output.setSwimlaneId(swinlaneId);
    }
}
