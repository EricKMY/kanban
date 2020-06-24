package domain.usecase;

import domain.adapter.presenter.board.create.CreateBoardPresenter;
import domain.adapter.presenter.card.create.CreateCardPresenter;
import domain.adapter.presenter.card.cycleTime.CalculateCycleTimePresenter;
import domain.adapter.presenter.card.move.MoveCardPresenter;
import domain.adapter.presenter.lane.stage.create.CreateStagePresenter;
import domain.adapter.presenter.lane.swimLane.create.CreateSwimLanePresenter;
import domain.adapter.presenter.workflow.create.CreateWorkflowPresenter;
import domain.model.DomainEventBus;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeInput;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeOutput;
import domain.usecase.card.calculateCycleTime.CalculateCycleTimeUseCase;
import domain.usecase.card.calculateCycleTime.CycleTimeModel;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.card.moveCard.MoveCardInput;
import domain.usecase.card.moveCard.MoveCardOutput;
import domain.usecase.card.moveCard.MoveCardUseCase;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import domain.usecase.lane.createSwimLane.CreateSwimLaneInput;
import domain.usecase.lane.createSwimLane.CreateSwimLaneOutput;
import domain.usecase.lane.createSwimLane.CreateSwimLaneUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;

public class TestUtility {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private IFlowEventRepository flowEventRepository;
    private DomainEventBus eventBus;

    public TestUtility(IBoardRepository boardRepository, IWorkflowRepository workflowRepository, ICardRepository cardRepository,IFlowEventRepository flowEventRepository, DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
        this.flowEventRepository = flowEventRepository;
        this.eventBus = eventBus;
    }

    public String createBoard(String userId, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardInput input = createBoardUseCase;
        CreateBoardOutput output = new CreateBoardPresenter();

        input.setUserId(userId);
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
        CreateStageInput input = createStageUseCase;
        CreateStageOutput output = new CreateStagePresenter();

        input.setWorkflowId(workflowId);
        input.setStageName(stageName);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }

    public String createStage(String workflowId, String parentId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository, eventBus);
        CreateStageInput input = createStageUseCase;
        CreateStageOutput output = new CreateStagePresenter();

        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentId);
        input.setStageName(stageName);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }

    public String createSwimLane(String workflowId, String parentId, String stageName) {

        CreateSwimLaneUseCase createSwimLaneUseCase = new CreateSwimLaneUseCase(workflowRepository, eventBus);
        CreateSwimLaneInput input = createSwimLaneUseCase;
        CreateSwimLaneOutput output = new CreateSwimLanePresenter();

        input.setSwimLaneName(stageName);
        input.setWorkflowId(workflowId);
        input.setParentLaneId(parentId);

        createSwimLaneUseCase.execute(input, output);

        return output.getSwimLaneId();
    }

    public void moveCard(String workflowId, String cardId, String laneId, String targetLaneId) {
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, cardRepository, eventBus);

        MoveCardInput input = moveCardUseCase;
        MoveCardOutput output = new MoveCardPresenter();

        input.setWorkflowId(workflowId);
        input.setOriginLaneId(laneId);
        input.setTargetLaneId(targetLaneId);
        input.setCardId(cardId);

        moveCardUseCase.execute(input, output);
    }

    public CycleTimeModel calculateCycleTime(String workflowId, String cardId, String beginningLaneId, String endingLaneId){
        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, eventBus);
        CalculateCycleTimeInput input = calculateCycleTimeUseCase;
        input.setWorkflowId(workflowId);
        input.setCardId(cardId);
        input.setBeginningStageId(beginningLaneId);
        input.setEndingStageId(endingLaneId);
        CalculateCycleTimeOutput output = new CalculateCycleTimePresenter();

        calculateCycleTimeUseCase.execute(input, output);
        return output.getCycleTimeModel();
    }

    public String createCard(String cardName, String workflowId, String laneId) {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);
        CreateCardInput input = createCardUseCase;
        CreateCardOutput output = new CreateCardPresenter();

        input.setCardName(cardName);
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        createCardUseCase.execute(input, output);

        return output.getCardId();
    }

}
