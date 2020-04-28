package domain.usecase.workflow;

import domain.adapter.board.BoardInMemoryRepository;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowInput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowOutput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommitWorkflowUseCaseTest {

    private BoardInMemoryRepository boardInMemoryRepository;
    private String baordId;

    @Before
    public void setup() {
        boardInMemoryRepository = new BoardInMemoryRepository();
        baordId = createBoard("kanban777", "kanbanSystem");
    }

    @Test
    public void commitWorkflow() {
        String workflowId = "W012345678";
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardInMemoryRepository);
        CommitWorkflowInput input = new CommitWorkflowInput();
        CommitWorkflowOutput output = new CommitWorkflowOutput();

        input.setWorkflowId(workflowId);
        input.setBoardId(baordId);

        commitWorkflowUseCase.execute(input, output);

        assertTrue(boardInMemoryRepository.findById(baordId).isWorkflowContained("W012345678"));
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
