package domain.usecase.card;

import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.presenter.card.commit.CommitCardPresenter;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Lane;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.commitCard.CommitCardInput;
import domain.usecase.card.commitCard.CommitCardOutput;
import domain.usecase.card.commitCard.CommitCardUseCase;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommitCardUseCaseTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private IDomainEventRepository domainEventRepository;
    private IFlowEventRepository flowEventRepository;
    private String workflowId;
    private String laneId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;


    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));

        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        String boardId = testUtility.createBoard("user777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        laneId = testUtility.createTopStage(workflowId, "developing");
    }

    @Test
    public void commit_a_Card_to_Workflow_aggregate() {
        String cardId = "C012345678";
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);

        CommitCardInput input = commitCardUseCase;
        CommitCardOutput output = new CommitCardPresenter();

        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);
        input.setCardId(cardId);

        Lane lane = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(laneId);

        assertEquals(0, lane.getCardList().size());

        commitCardUseCase.execute(input, output);

        assertNotNull(output.getCardId());

        lane = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(laneId);

        assertEquals(1, lane.getCardList().size());
        assertTrue(lane.isCardContained(cardId));
    }
}
