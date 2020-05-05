package domain.usecase.card;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.model.card.Card;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
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
    private String workflowId;
    private String laneId;
    private DomainEventBus eventBus;
    private TestUtility testUtility;


    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository));
        testUtility = new TestUtility(boardRepository, workflowRepository, eventBus);

        String boardId = testUtility.createBoard("kanban777", "kanban");
        workflowId = testUtility.createWorkflow(boardId, "defaultWorkflow");
        laneId = testUtility.createTopStage(workflowId, "developing");
    }

    @Test
    public void createCard() {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);

        CreateCardInput input = new CreateCardInput();
        CreateCardOutput output = new CreateCardOutput();

        input.setCardName("firstEvent");
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        createCardUseCase.execute(input, output);

        assertEquals(0, cardRepository
                .findById(output.getCardId())
                .getDomainEvents()
                .size());

        assertEquals(workflowId, cardRepository
                .findById(output.getCardId())
                .getWorkflowId());

        assertEquals(laneId, cardRepository
                .findById(output.getCardId())
                .getLaneId());

        assertNotNull(cardRepository
                .findById(output.getCardId())
                .getId());
    }

    @Test
    public void cardEventHandler() {

        Card card = new Card("firstEvent", laneId, workflowId);

        assertEquals(1, card.getDomainEvents().size());
        assertEquals("Card Created: firstEvent", card.getDomainEvents().get(0).getDetail());
    }

    @Test
    public void cardEventHandlerError() {

        Card card = new Card("firstEvent", "0", "0");
        cardRepository.save(card);

    }
}
