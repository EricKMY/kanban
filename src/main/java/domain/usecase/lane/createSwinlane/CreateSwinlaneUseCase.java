package domain.usecase.lane.createSwinlane;

import domain.adapter.board.BoardInMemoryRepository;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IWorkflowRepository;

public class CreateSwinlaneUseCase {
    private IWorkflowRepository workflowRepository;
    private BoardInMemoryRepository boardInMemoryRepository;
    private Workflow workflow;


    public CreateSwinlaneUseCase(IWorkflowRepository workflowRepository, BoardInMemoryRepository boardInMemoryRepository) {
        this.workflowRepository = workflowRepository;
        this.boardInMemoryRepository = boardInMemoryRepository;
    }

    public void execute(CreateSwinlaneInput input, CreateSwinlaneOutput output) {
        workflow = workflowRepository.findById(input.getWorkflowId());
        String swinlaneId = workflow.createSwinlane(input.getSwinlaneName(), input.getParentLaneId());

        workflowRepository.save(workflow);

        output.setSwinlaneId(swinlaneId);
    }
}
