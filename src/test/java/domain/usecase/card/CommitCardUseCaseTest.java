package domain.usecase.card;

import domain.adapter.board.BoardRepository;
import domain.adapter.card.CardRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.adapter.workflow.WorkflowRepository;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.card.commitCard.CommitCardInput;
import domain.usecase.card.commitCard.CommitCardOutput;
import domain.usecase.card.commitCard.CommitCardUseCase;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.lane.createStage.CreateStageInput;
import domain.usecase.workflow.lane.createStage.CreateStageOutput;
import domain.usecase.workflow.lane.createStage.CreateStageUseCase;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommitCardUseCaseTest {
    private BoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String workflowId;
    private String laneId;


    @Before
    public void setup() {
        boardRepository = new BoardRepository();
        workflowRepository = new WorkflowRepository();
        String boardId = createBoard("kanban777", "kanban");
        workflowId = createWorkflow(boardId, "defaultWorkflow");
        laneId = createStage(workflowId, "developing");
    }

    @Test
    public void commitCard() {
        String cardId = "C012345678";
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(
                workflowRepository);

        CommitCardInput input = new CommitCardInput();
        CommitCardOutput output = new CommitCardOutput();

        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);
        input.setCardId(cardId);

        commitCardUseCase.execute(input, output);
        assertEquals(laneId, workflowRepository.findById(workflowId).findLaneByCardId(cardId).getLaneId());
    }

    private String createWorkflow(String boardId, String workflowName) {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardRepository);

        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();

    }

    private String createStage(String workflowId, String stageName) {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, boardRepository);
        CreateStageInput input = new CreateStageInput();
        CreateStageOutput output = new CreateStageOutput();

        input.setWorkflowId(workflowId);
        input.setStageName(stageName);

        createStageUseCase.execute(input, output);

        return output.getStageId();
    }

    private String createBoard(String username, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername(username);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);
        return output.getBoardId();
    }
}
