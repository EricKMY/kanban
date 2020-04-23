package domain.usecase.workflow;

import domain.adapter.board.BoardRepository;
import domain.adapter.workflow.WorkflowRepository;
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
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardRepository);
        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();

        input.setBoardId(baordId);
        input.setWorkflowName("defaultWorkflow");

        createWorkflowUseCase.execute(input, output);

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
    }
}
