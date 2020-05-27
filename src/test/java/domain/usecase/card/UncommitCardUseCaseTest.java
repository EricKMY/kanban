package domain.usecase.card;

import domain.adapter.FlowEventInMemoryRepository;
import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardInMemoryRepository;
import domain.adapter.card.commitCard.CommitCardPresenter;
import domain.adapter.card.uncommitCard.UncommitCardPresenter;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.model.workflow.Lane;
import domain.usecase.TestUtility;
import domain.usecase.card.commitCard.CommitCardInput;
import domain.usecase.card.commitCard.CommitCardOutput;
import domain.usecase.card.commitCard.CommitCardUseCase;
import domain.usecase.card.uncommitCard.UncommitCardInput;
import domain.usecase.card.uncommitCard.UncommitCardOutput;
import domain.usecase.card.uncommitCard.UncommitCardUseCase;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UncommitCardUseCaseTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String workflowId;
    private String laneId;
    private String cardId;
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
        cardId = "C012345678";
        commit_a_Card_to_Workflow_aggregate(cardId);
    }

    public void commit_a_Card_to_Workflow_aggregate(String cardId) {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);

        CommitCardInput input = (CommitCardInput) commitCardUseCase;
        CommitCardOutput output = new CommitCardPresenter();

        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);
        input.setCardId(cardId);

        commitCardUseCase.execute(input, output);
    }

    @Test
    public void uncommit_a_Card_from_Workflow_aggregate() {
        UncommitCardUseCase uncommitCardUseCase = new UncommitCardUseCase(workflowRepository, eventBus);

        UncommitCardInput input = (UncommitCardInput) uncommitCardUseCase;
        UncommitCardOutput output = new UncommitCardPresenter();

        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);
        input.setCardId(cardId);

        Lane lane = WorkflowDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(laneId);

        assertEquals(1, lane.getCardList().size());
        assertTrue(lane.isCardContained(cardId));

        uncommitCardUseCase.execute(input, output);

        lane = WorkflowDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(laneId);

        assertEquals(0, lane.getCardList().size());
        assertFalse(lane.isCardContained(cardId));
    }
}
