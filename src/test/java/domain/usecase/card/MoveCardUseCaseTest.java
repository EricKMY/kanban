package domain.usecase.card;

import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.card.createCard.CreateCardPresenter;
import domain.adapter.card.moveCard.MoveCardPresenter;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.model.workflow.Workflow;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.card.moveCard.MoveCardInput;
import domain.usecase.card.moveCard.MoveCardOutput;
import domain.usecase.card.moveCard.MoveCardUseCase;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveCardUseCaseTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private String workflowId;
    private String backlogLaneId;
    private String planningLaneId;
    private String cardName;
    private String cardId;
    private IFlowEventRepository flowEventRepository;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        backlogLaneId = testUtility.createTopStage(workflowId, "backlog");
        planningLaneId = testUtility.createTopStage(workflowId, "planning");
        cardName = "implement MoveCardUseCase";
        cardId = create_a_card_in_lane(cardName, backlogLaneId);

    }

    @Test
    public void move_card_from_backlog_stage_to_planning_stage() {
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, cardRepository, eventBus);

        MoveCardInput input = (MoveCardInput) moveCardUseCase;
        MoveCardOutput output = new MoveCardPresenter();

        input.setWorkflowId(workflowId);
        input.setLaneId(backlogLaneId);
        input.setTargetLaneId(planningLaneId);
        input.setCardId(cardId);

        Workflow workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId));
        assertTrue(workflow.findLaneById(backlogLaneId).isCardContained(cardId));
        assertFalse(workflow.findLaneById(planningLaneId).isCardContained(cardId));

        moveCardUseCase.execute(input, output);

        assertEquals(planningLaneId, CardDTOConverter
                .toEntity(cardRepository.findById(cardId))
                .getLaneId());

        workflow = WorkflowDTOConverter.toEntity(workflowRepository.findById(workflowId));
        assertFalse(workflow.findLaneById(backlogLaneId).isCardContained(cardId));
        assertTrue(workflow.findLaneById(planningLaneId).isCardContained(cardId));
    }


    public String create_a_card_in_lane(String cardName, String laneId) {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);

        CreateCardInput input = (CreateCardInput) createCardUseCase;
        CreateCardOutput output = new CreateCardPresenter();

        input.setCardName(cardName);
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        createCardUseCase.execute(input, output);

        return output.getCardId();
    }
}