package domain.usecase.lane.createSwimlane;

import domain.model.workflow.Workflow;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;

public class CreateSwimlaneUseCase {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private Workflow workflow;


    public CreateSwimlaneUseCase(IWorkflowRepository workflowRepository, IBoardRepository boardRepository) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
    }

    public void execute(CreateSwimlaneInput input, CreateSwimlaneOutput output) {
        workflow = workflowRepository.findById(input.getWorkflowId());
        String swinlaneId = workflow.createSwinlane(input.getSwinlaneName(), input.getParentLaneId());

        workflowRepository.save(workflow);

        output.setSwinlaneId(swinlaneId);
    }
}
