package domain.usecase.workflow;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.createWorkflow.CreateWorkflowInput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowOutput;
import domain.usecase.workflow.createWorkflow.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateWorkflowUseCaseTest {

    private BoardInMemoryRepository boardInMemoryRepository;
    private IWorkflowRepository workflowRepository;
    private String baordId;

    @Before
    public void setup() {
        boardInMemoryRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();

        baordId = createBoard("kanban777", "kanbanSystem");
    }

    @Test
    public void createWorkflow(){
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardInMemoryRepository);
        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();

        input.setBoardId(baordId);
        input.setWorkflowName("defaultWorkflow");

        createWorkflowUseCase.execute(input, output);

        assertEquals(baordId, workflowRepository.findById(output.getWorkflowId()).getBoardId());
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
