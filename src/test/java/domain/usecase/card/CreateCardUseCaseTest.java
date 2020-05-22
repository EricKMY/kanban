package domain.usecase.card;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardRepository;
import domain.adapter.card.createCard.CreateCardPresenter;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.model.workflow.Lane;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.ICardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.WorkflowDTOConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCardUseCaseTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private String workflowId;
    private String laneId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;
    private CreateCardUseCase createCardUseCase;
    private CreateCardInput input;
    private CreateCardOutput output;
    private String cardName;


    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        testUtility = new TestUtility(boardRepository, workflowRepository, eventBus);

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        laneId = testUtility.createTopStage(workflowId, "developing");
        cardName = "firstEvent";
    }

    @Test
    public void create_a_Card() {
        createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);

        input = (CreateCardInput)createCardUseCase;
        output = new CreateCardPresenter();

        input.setCardName(cardName);
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        createCardUseCase.execute(input, output);

        assertEquals(workflowId, CardDTOConverter.toEntity(cardRepository
                .findById(output.getCardId()))
                .getWorkflowId());

        assertEquals(laneId,CardDTOConverter.toEntity(cardRepository
                .findById(output.getCardId()))
                .getLaneId());

        assertNotNull(CardDTOConverter.toEntity(cardRepository
                .findById(output.getCardId()))
                .getId());
    }

    @Test
    public void create_a_Card_should_commit_to_its_Lane() {
        Lane lane = WorkflowDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .getLaneMap()
                .get(laneId);

        assertEquals(0, lane.getCardList().size());

        create_a_Card();

        lane = WorkflowDTOConverter
                .toEntity(workflowRepository.findById(workflowId))
                .getLaneMap()
                .get(laneId);

        assertEquals(1, lane.getCardList().size());

        assertEquals(cardName, CardDTOConverter
                .toEntity(cardRepository.findById(lane.getCardList().get(0)))
                .getName());

        assertEquals(laneId, CardDTOConverter
                .toEntity(cardRepository.findById(lane.getCardList().get(0)))
                .getLaneId());

        assertEquals(workflowId, CardDTOConverter
                .toEntity(cardRepository.findById(lane.getCardList().get(0)))
                .getWorkflowId());
    }
 }
