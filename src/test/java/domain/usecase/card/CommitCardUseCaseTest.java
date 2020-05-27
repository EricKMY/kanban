package domain.usecase.card;

import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.card.commitCard.CommitCardPresenter;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.model.workflow.Lane;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.commitCard.CommitCardInput;
import domain.usecase.card.commitCard.CommitCardOutput;
import domain.usecase.card.commitCard.CommitCardUseCase;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommitCardUseCaseTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String workflowId;
    private String laneId;
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
        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        laneId = testUtility.createTopStage(workflowId, "developing");
    }

    @Test
    public void commit_a_Card_to_Workflow_aggregate() {
        String cardId = "C012345678";
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);

        CommitCardInput input = (CommitCardInput) commitCardUseCase;
        CommitCardOutput output = new CommitCardPresenter();

        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);
        input.setCardId(cardId);

        Lane lane = WorkflowDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(laneId);

        assertEquals(0, lane.getCardList().size());
        assertFalse(lane.isCardContained(cardId));

        commitCardUseCase.execute(input, output);

        lane = WorkflowDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(laneId);

        assertEquals(1, lane.getCardList().size());
        assertTrue(lane.isCardContained(cardId));
    }
}
