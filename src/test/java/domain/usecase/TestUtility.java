package domain.usecase;

import domain.model.DomainEventBus;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import domain.usecase.lane.createSwimlane.CreateSwimlaneInput;
import domain.usecase.lane.createSwimlane.CreateSwimlaneOutput;
import domain.usecase.lane.createSwimlane.CreateSwimlaneUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;

public class TestUtility {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;

    public TestUtility(IBoardRepository boardRepository, IWorkflowRepository workflowRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    public String createBoard(String username, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername(username);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);
        return output.getBoardId();
    }

    public String createWorkflow(String boardId, String workflowName) {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, domainEventBus);

        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();

    }

    public String createTopStage(String workflowId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setWorkflowId(workflowId);
        input.setStageName(stageName);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }

    public String createStage(String workflowId, String parentId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentId);
        input.setStageName(stageName);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }

    public String createSwimLane(String workflowId, String parentId, String stageName) {

        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository, boardRepository);
        CreateSwimlaneInput input = new CreateSwimlaneInput();
        CreateSwimlaneOutput output = new CreateSwimlaneOutput();

        input.setSwinlaneName(stageName);
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentId);

        createSwimlaneUseCase.execute(input, output);

        return output.getSwinlaneId();
    }
}
