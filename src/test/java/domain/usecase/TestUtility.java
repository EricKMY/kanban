package domain.usecase;

import domain.adapter.board.createBoard.CreateBoardPresenter;
import domain.adapter.card.createCard.CreateCardPresenter;
import domain.adapter.workflow.createWorkflow.CreateWorkflowPresenter;
import domain.model.DomainEventBus;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.card.CardDTOConverter;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import domain.usecase.lane.createSwimLane.CreateSwimLaneInput;
import domain.usecase.lane.createSwimLane.CreateSwimLaneOutput;
import domain.usecase.lane.createSwimLane.CreateSwimLaneUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestUtility {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public TestUtility(IBoardRepository boardRepository, IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public String createBoard(String username, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardInput input = createBoardUseCase;
        CreateBoardOutput output = new CreateBoardPresenter();

        input.setUsername(username);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);
        return output.getBoardId();
    }

    public String createWorkflow(String boardId, String workflowName) {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, eventBus);

        CreateWorkflowInput input = (CreateWorkflowInput) createWorkflowUseCase;
        CreateWorkflowOutput output = new CreateWorkflowPresenter();
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();

    }

    public String createTopStage(String workflowId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setWorkflowId(workflowId);
        input.setStageName(stageName);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }

    public String createStage(String workflowId, String parentId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentId);
        input.setStageName(stageName);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }

    public String createSwimLane(String workflowId, String parentId, String stageName) {

        CreateSwimLaneUseCase createSwimLaneUseCase = new CreateSwimLaneUseCase(workflowRepository, boardRepository, eventBus);
        CreateSwimLaneInput input = new CreateSwimLaneInput();
        CreateSwimLaneOutput output = new CreateSwimLaneOutput();

        input.setSwimLaneName(stageName);
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentId);

        createSwimLaneUseCase.execute(input, output);

        return output.getSwimLaneId();
    }

}
