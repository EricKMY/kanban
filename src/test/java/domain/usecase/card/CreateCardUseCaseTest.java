package domain.usecase.card;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCardUseCaseTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private CardRepository cardRepository;
    private String workflowId;
    private String laneId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;


    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository));
        testUtility = new TestUtility(boardRepository, workflowRepository, eventBus);

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        laneId = testUtility.createTopStage(workflowId, "developing");
    }

    @Test
    public void createCard() {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(
                workflowRepository,
                cardRepository);

        CreateCardInput input = new CreateCardInput();
        CreateCardOutput output = new CreateCardOutput();

        input.setCardName("firstEvent");
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        createCardUseCase.execute(input, output);
        assertNotNull(cardRepository.findById(output.getCardId()));
        assertEquals(workflowId, cardRepository
                .findById(output.getCardId())
                .getWorkflowId());
    }
}
