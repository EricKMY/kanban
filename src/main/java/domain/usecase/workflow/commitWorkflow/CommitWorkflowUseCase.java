package domain.usecase.workflow.commitWorkflow;
import domain.adapter.board.BoardInMemoryRepository;
import domain.model.board.Board;
import domain.usecase.repository.IBoardRepository;

public class CommitWorkflowUseCase {
    private IBoardRepository boardRepository;

    public CommitWorkflowUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public void execute(CommitWorkflowInput input, CommitWorkflowOutput output) {
        Board board = boardRepository.findById(input.getBoardId());
        board.addWorkflow(input.getWorkflowId());
        boardRepository.save(board);
    }
}
