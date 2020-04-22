package domain.usecase.workflow.createWorkflow;

import domain.adapter.board.BoardRepository;
import domain.adapter.card.CardRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.adapter.workflow.WorkflowRepository;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateWorkflowUseCaseTest {

    private BoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private String baordId;

    @Before
    public void setup() {
        boardRepository = new BoardRepository();
        workflowRepository = new WorkflowRepository();

        baordId = createBoard("kanban777", "kanbanSystem");
    }

    @Test
    public void createWorkflow(){
<<<<<<< HEAD
        WorkflowInMemoryRepository workflowInMemoryRepository = new WorkflowInMemoryRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowInMemoryRepository);
=======
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardRepository);
>>>>>>> b6ba6d640c3b25fc7118ce02590a237543588086
        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();

        input.setBoardId(baordId);
        input.setWorkflowName("defaultWorkflow");

        createWorkflowUseCase.execute(input, output);

<<<<<<< HEAD
        assertEquals("board00000001", workflowInMemoryRepository.findById(output.getWorkflowId()).getBoardId());
=======
        assertEquals(baordId, workflowRepository.findById(output.getWorkflowId()).getBoardId());
    }

    private String createBoard(String username, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setUsername(username);
        input.setBoardName(boardName);

        createBoardUseCase.execute(input, output);
        return output.getBoardId();
>>>>>>> b6ba6d640c3b25fc7118ce02590a237543588086
    }
}
