package domain.usecase.workflow.lane;

import domain.adapter.board.BoardRepository;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CreateSwinlaneUseCase {
    private IWorkflowRepository workflowRepository;
    private BoardRepository boardRepository;
    private Workflow workflow;


    public CreateSwinlaneUseCase(IWorkflowRepository workflowRepository, BoardRepository boardRepository) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
    }

    public void execute(CreateSwinlaneInput input, CreateSwinlaneOutput output) {
        workflow = workflowRepository.findById(input.getWorkflowId());
        String swinlaneId = workflow.createSwinlane(input.getSwinlaneName(), input.getParentLaneId());

        workflowRepository.save(workflow);

        output.setSwinlaneId(swinlaneId);
    }
}
