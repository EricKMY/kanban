package domain.usecase.card;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.card.CardRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.card.createCard.CreateCardInput;
import domain.usecase.card.createCard.CreateCardOutput;
import domain.usecase.card.createCard.CreateCardUseCase;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import domain.usecase.lane.createStage.CreateStageInput;
import domain.usecase.lane.createStage.CreateStageOutput;
import domain.usecase.lane.createStage.CreateStageUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCardUseCaseTest {

    private BoardInMemoryRepository boardInMemoryRepository;
    private IWorkflowRepository workflowRepository;
    private CardRepository cardRepository;
    private String workflowId;
    private String laneId;


    @Before
    public void setup() {
        boardInMemoryRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        cardRepository = new CardRepository();

        String boardId = createBoard("kanban777", "kanban");
        workflowId = createWorkflow(boardId, "defaultWorkflow");
        laneId = createStage(workflowId, "developing");
    }

    @Test
    public void createCard() {
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(
                workflowRepository,
                cardRepository);

        CreateCardInput input = new CreateCardInput();
        CreateCardOutput output = new CreateCardOutput();

        input.setCardName("firstEvent");
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        createCardUseCase.execute(input, output);
        assertNotNull(cardRepository.findById(output.getCardId()));
        assertEquals(workflowId, cardRepository
                .findById(output.getCardId())
                .getWorkflowId());
    }

    private String createWorkflow(String boardId, String workflowName) {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardInMemoryRepository);

        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();

    }


    private String createStage(String workflowId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardInMemoryRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setWorkflowId(workflowId);
        input.setStageName(stageName);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }

    private String createBoard(String username, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardInMemoryRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername(username);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);
        return output.getBoardId();
    }
}
