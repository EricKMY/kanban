package domain.usecase.board;

import domain.adapter.repository.board.BoardInMemoryRepository;
import domain.adapter.presenter.board.create.CreateBoardPresenter;
import domain.adapter.repository.board.converter.BoardRepositoryDTOConverter;
import domain.adapter.repository.domainEvent.DomainEventInMemoryRepository;
import domain.adapter.repository.workflow.WorkflowInMemoryRepository;
import domain.model.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.usecase.DomainEventHandler;
import domain.usecase.DomainEventSaveHandler;
import domain.usecase.board.createBoard.CreateBoardInput;
import domain.usecase.board.createBoard.CreateBoardOutput;
import domain.usecase.board.createBoard.CreateBoardUseCase;
import domain.usecase.domainEvent.repository.IDomainEventRepository;
import domain.usecase.repository.IBoardRepository;
import domain.usecase.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CreateBoardUseCaseTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private IDomainEventRepository domainEventRepository;

    @Before
    public void setup() {
        boardRepository = new BoardInMemoryRepository();
        workflowRepository = new WorkflowInMemoryRepository();
        domainEventRepository = new DomainEventInMemoryRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventSaveHandler(domainEventRepository));
    }

    @Test
    public void create_a_Board_should_succeed(){
        BoardInMemoryRepository boardInMemoryRepository = new BoardInMemoryRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardInMemoryRepository, eventBus);

        CreateBoardInput input = createBoardUseCase;
        CreateBoardOutput output = new CreateBoardPresenter();

        input.setUserId("user777");
        input.setBoardName("kanbanSystem");

        createBoardUseCase.execute(input, output);

        assertNotNull(output.getBoardId());

        Board board = BoardRepositoryDTOConverter.toEntity(boardInMemoryRepository.findById(output.getBoardId()));

        assertNotNull(board);
        assertEquals("user777", board.getUserId());
        assertEquals(output.getBoardId(), board.getId());
        assertEquals("kanbanSystem", board.getName());
        assertEquals(0, board.getWorkflowList().size());
    }
}
