package domain.model.card;

import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private String workflowId;
    private String laneId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private IFlowEventRepository flowEventRepository;
    private IDomainEventRepository domainEventRepository;


    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));
        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        laneId = testUtility.createTopStage(workflowId, "developing");
    }

    @Test
    public void create_a_Card_should_generate_a_CardCreated_event() {

        Card card = new Card("firstEvent", laneId, workflowId);

        assertEquals(1, card.getDomainEvents().size());
        assertEquals("Card Created: firstEvent", card.getDomainEvents().get(0).getDetail());
    }

//    @Test
//    public void commit_a_Card_to_a_fake_Workflow_aggregate_should_throw_exception() {
//
//        Card card = new Card("firstEvent", laneId, "fake");
//        cardRepository.save(CardDTOConverter.toDTO(card));

        // should throw exception here, but don't know how to handel.
        // the exception will be catch by google's EventBus exception handler.
//        eventBus.postAll(card);
//    }
}
