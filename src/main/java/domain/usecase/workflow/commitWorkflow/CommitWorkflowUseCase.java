package domain.usecase.workflow.commitWorkflow;
import domain.adapter.board.BoardRepository;
import domain.model.board.Board;

public class CommitWorkflowUseCase {
    private BoardRepository boardRepository;

    public CommitWorkflowUseCase(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public void execute(CommitWorkflowInput input, CommitWorkflowOutput output) {
        Board board = boardRepository.findById(input.getBoardId());
        board.addWorkflow(input.getWorkflowId());
        boardRepository.save(board);
    }
}
