package domain.usecase.card;

import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.presenter.card.create.CreateCardPresenter;
import domain.adapter.presenter.card.move.MoveCardPresenter;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.card.moveCard.MoveCardInput;
import domain.usecase.card.moveCard.MoveCardOutput;
import domain.usecase.card.moveCard.MoveCardUseCase;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
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
    private IDomainEventRepository domainEventRepository;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));

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

        MoveCardInput input = moveCardUseCase;
        MoveCardOutput output = new MoveCardPresenter();

        input.setWorkflowId(workflowId);
        input.setLaneId(backlogLaneId);
        input.setTargetLaneId(planningLaneId);
        input.setCardId(cardId);

        Workflow workflow = WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(workflowId));
        assertTrue(workflow.findLaneById(backlogLaneId).isCardContained(cardId));
        assertFalse(workflow.findLaneById(planningLaneId).isCardContained(cardId));

        moveCardUseCase.execute(input, output);

        assertNotNull(output.getCardId());
        assertEquals(planningLaneId, CardDTOConverter
                .toEntity(cardRepository.findById(cardId))
                .getLaneId());

        workflow = WorkflowRepositoryDTOConverter.toEntity(workflowRepository.findById(workflowId));
        assertFalse(workflow.findLaneById(backlogLaneId).isCardContained(cardId));
        assertTrue(workflow.findLaneById(planningLaneId).isCardContained(cardId));
    }

    private String create_a_card_in_lane(String cardName, String laneId) {
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