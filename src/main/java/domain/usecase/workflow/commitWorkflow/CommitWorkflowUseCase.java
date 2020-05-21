package domain.usecase.workflow.commitWorkflow;
import domain.model.DomainEventBus;
import domain.model.board.Board;
import domain.usecase.board.BoardRepositoryDTOConverter;
import domain.usecase.repository.IBoardRepository;

public class CommitWorkflowUseCase implements CommitWorkflowInput {
    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;
    private String workflowId;
    private String boardId;

    public CommitWorkflowUseCase(IBoardRepository boardRepository,
                                 DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }


    public void execute(CommitWorkflowInput input, CommitWorkflowOutput output) {
        Board board = BoardRepositoryDTOConverter.toEntity(boardRepository.findById(input.getBoardId()));
        board.addWorkflow(input.getWorkflowId());

        boardRepository.save(BoardRepositoryDTOConverter.toDTO(board));
        eventBus.postAll(board);

        output.setWorkflowId(input.getWorkflowId());
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
