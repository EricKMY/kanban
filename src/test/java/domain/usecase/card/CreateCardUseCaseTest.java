package domain.usecase.card;

import domain.adapter.repository.card.converter.CardRepositoryDTOConverter;
import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.presenter.card.create.CreateCardPresenter;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.card.Card;
import domain.model.aggregate.workflow.Lane;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCardUseCaseTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private IFlowEventRepository flowEventRepository;
    private IDomainEventRepository domainEventRepository;
    private String workflowId;
    private String laneId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private String cardName;


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

        String boardId = testUtility.createBoard("user777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        laneId = testUtility.createTopStage(workflowId, "developing");
        cardName = "firstEvent";
    }

    @Test
    public void create_a_Card_should_succeed() {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);
        CreateCardInput input = createCardUseCase;
        CreateCardOutput output = new CreateCardPresenter();

        input.setCardName(cardName);
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        createCardUseCase.execute(input, output);

        assertNotNull(output.getCardId());

        Card card = CardRepositoryDTOConverter.toEntity(cardRepository
                .findById(output.getCardId()));

        assertEquals(workflowId, card.getWorkflowId());
        assertEquals(laneId, card.getLaneId());
        assertEquals(output.getCardId(), card.getId());
        assertEquals(cardName, card.getName());
    }

    @Test
    public void create_a_Card_should_commit_to_its_Lane() {
        Lane lane = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(laneId);

        assertEquals(0, lane.getCardList().size());

        testUtility.createCard(cardName, workflowId, laneId);

        lane = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(laneId);

        assertEquals(1, lane.getCardList().size());
    }
 }
