package domain.model.workflow;

import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
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

        boardId = testUtility.createBoard("kanban777", "kanbanSystem");
    }

    @Test
    public void create_a_Workflow_should_generate_a_WorkFlowCreated_event() {
        Workflow workflow = new Workflow("defaultWorkflow", boardId);
        assertEquals(1, workflow.getDomainEvents().size());
        assertEquals("Workflow Created: defaultWorkflow", workflow.getDomainEvents().get(0).getDetail());

    }
}
