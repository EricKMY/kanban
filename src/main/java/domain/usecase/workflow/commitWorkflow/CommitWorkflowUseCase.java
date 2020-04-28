package domain.usecase.workflow.commitWorkflow;
import domain.adapter.board.BoardInMemoryRepository;
import domain.model.board.Board;

public class CommitWorkflowUseCase {
    private BoardInMemoryRepository boardInMemoryRepository;

    public CommitWorkflowUseCase(BoardInMemoryRepository boardInMemoryRepository) {
        this.boardInMemoryRepository = boardInMemoryRepository;
    }


    public void execute(CommitWorkflowInput input, CommitWorkflowOutput output) {
        Board board = boardInMemoryRepository.findById(input.getBoardId());
        board.addWorkflow(input.getWorkflowId());
        boardInMemoryRepository.save(board);
    }
}
