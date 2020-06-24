package domain.usecase.card;

import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.flowEvent.FlowEventInMemoryRepository;
import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.repository.card.CardInMemoryRepository;
import domain.adapter.presenter.card.commit.CommitCardPresenter;
import domain.adapter.presenter.card.uncommit.UncommitCardPresenter;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.adapter.repository.workflow.converter.WorkflowRepositoryDTOConverter;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Lane;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.commitCard.CommitCardInput;
import domain.usecase.card.commitCard.CommitCardOutput;
import domain.usecase.card.commitCard.CommitCardUseCase;
import domain.usecase.card.uncommitCard.UncommitCardInput;
import domain.usecase.card.uncommitCard.UncommitCardOutput;
import domain.usecase.card.uncommitCard.UncommitCardUseCase;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UncommitCardUseCaseTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private IFlowEventRepository flowEventRepository;
    private IDomainEventRepository domainEventRepository;
    private String workflowId;
    private String laneId;
    private String cardId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;


    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardInMemoryRepository();
        flowEventRepository = new FlowEventInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();


        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));

        testUtility = new TestUtility(boardRepository, workflowRepository, cardRepository, flowEventRepository, eventBus);

        String boardId = testUtility.createBoard("user777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        laneId = testUtility.createTopStage(workflowId, "developing");
        cardId = "C012345678";
        commit_a_Card_to_Workflow_aggregate(cardId);
    }

    @Test
    public void uncommit_a_Card_from_Workflow_aggregate() {
        UncommitCardUseCase uncommitCardUseCase = new UncommitCardUseCase(workflowRepository, eventBus);

        UncommitCardInput input = uncommitCardUseCase;
        UncommitCardOutput output = new UncommitCardPresenter();

        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);
        input.setCardId(cardId);

        Lane lane = WorkflowRepositoryDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .findLaneById(laneId);

        assertEquals(1, lane.getCardList().size());
        assertTrue(lane.isCardContained(cardId));

        uncommitCardUseCase.execute(input, output);

        assertNotNull(output.getCardId());
        assertEquals(0, lane.getCardList().size());
        assertFalse(lane.isCardContained(cardId));
    }

    private void commit_a_Card_to_Workflow_aggregate(String cardId) {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);

        CommitCardInput input = commitCardUseCase;
        CommitCardOutput output = new CommitCardPresenter();

        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);
        input.setCardId(cardId);

        commitCardUseCase.execute(input, output);
    }
}
