package domain.model.aggregate.workflow;

import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.event.CardCommitted;
import domain.model.aggregate.workflow.event.WorkflowCreated;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorkflowTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String boardId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        boardId = testUtility.createBoard("user777", "kanbanSystem");
    }

    @Test
    public void create_a_Workflow_should_generate_a_WorkFlowCreated_event() {
        Workflow workflow = new Workflow("defaultWorkflow", boardId);
        assertEquals(1, workflow.getDomainEvents().size());
        assertEquals(WorkflowCreated.class, workflow.getDomainEvents().get(0).getClass());

        WorkflowCreated workflowCreated = (WorkflowCreated)workflow.getDomainEvents().get(0);

        assertEquals(boardId, workflowCreated.getBoardId());
        assertEquals(workflow.getId(), workflowCreated.getWorkflowId());
        assertEquals(workflow.getName(), workflowCreated.getWorkflowName());
        assertEquals("Workflow Created: defaultWorkflow", workflowCreated.getDetail());
    }


    @Test
    public void commit_Card_should_generate_a_CardCommitted_event() {
        Workflow workflow = new Workflow("defaultWorkflow", boardId);
        String stageId = workflow.createStage("Backlog");

        assertEquals(0, workflow.getLaneMap().get(stageId).getCardList().size());

        workflow.commitCard("1", stageId);

        assertEquals(1, workflow.getLaneMap().get(stageId).getCardList().size());

        assertEquals(3, workflow.getDomainEvents().size());
        assertEquals(CardCommitted.class, workflow.getDomainEvents().get(2).getClass());

        CardCommitted cardCommitted = (CardCommitted)workflow.getDomainEvents().get(2);

        assertEquals(workflow.getLaneMap().get(stageId).getCardList().get(0), cardCommitted.getCardId());
        assertEquals(workflow.getId(), cardCommitted.getWorkflowId());
        assertEquals(stageId, cardCommitted.getLaneId());
        assertEquals("Card Committed 1 to Lane " + stageId, cardCommitted.getDetail());

    }
}
