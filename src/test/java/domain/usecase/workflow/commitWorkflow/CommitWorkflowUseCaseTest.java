package domain.usecase.workflow.commitWorkflow;

import domain.adapter.board.BoardRepository;
import domain.adapter.workflow.WorkflowRepository;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommitWorkflowUseCaseTest {

    private BoardRepository boardRepository;
    private String baordId;

    @Before
    public void setup() {
        boardRepository = new BoardRepository();
        baordId = createBoard("kanban777", "kanbanSystem");
    }

    @Test
    public void commitWorkflow() {
        String workflowId = "W012345678";
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository);
        CommitWorkflowInput input = new CommitWorkflowInput();
        CommitWorkflowOutput output = new CommitWorkflowOutput();

        input.setWorkflowId(workflowId);
        input.setBoardId(baordId);

        commitWorkflowUseCase.execute(input, output);

        assertTrue(boardRepository.findById(baordId).isWorkflowContained("W012345678"));
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
