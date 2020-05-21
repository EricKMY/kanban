package domain.usecase.workflow;

import domain.adapter.board.BoardInMemoryRepository;
import domain.adapter.workflow.WorkflowInMemoryRepository;
import domain.adapter.workflow.commitWorkflow.CommitWorkflowPresenter;
import domain.model.DomainEventBus;
import domain.usecase.DomainEventHandler;
import domain.usecase.TestUtility;
import domain.usecase.board.BoardRepositoryDTOConverter;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowInput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowOutput;
import domain.usecase.workflow.commitWorkflow.CommitWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommitWorkflowUseCaseTest {

    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;
    private String boardId;
    private TestUtility testUtility;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        eventBus = new DomainEventBus();

        IWorkflowRepository workflowRepository = new WorkflowInMemoryRepository();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        testUtility = new TestUtility(boardRepository, workflowRepository, eventBus);

        boardId = testUtility.createBoard("kanban777", "kanbanSystem");
    }

    @Test
    public void commit_a_Workflow_to_Board_aggregate() {
        String workflowId = "W012345678";
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);
        CommitWorkflowInput input = (CommitWorkflowInput) commitWorkflowUseCase;
        CommitWorkflowOutput output = new CommitWorkflowPresenter();

        input.setWorkflowId(workflowId);
        input.setBoardId(boardId);
        commitWorkflowUseCase.execute(input, output);

        assertTrue(BoardRepositoryDTOConverter.toEntity(
                boardRepository.findById(boardId)).isWorkflowContained("W012345678"));
    }
}
