package domain.usecase.lane.createStage;

import domain.adapter.board.BoardInMemoryRepository;
import domain.model.workflow.Workflow;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;

public class CreateStageUseCase {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private Workflow workflow;

    public CreateStageUseCase(IWorkflowRepository workflowRepository, IBoardRepository boardRepository) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {

        workflow = workflowRepository.findById(input.getWorkflowId());
        String stageId;
        if (input.getParentLaneId() == null){
            stageId = workflow.createStage(input.getStageName());
        }else{
            stageId = workflow.createStage(input.getStageName(), input.getParentLaneId());
        }

        workflowRepository.save(workflow);

        output.setStageId(stageId);
    }
}
