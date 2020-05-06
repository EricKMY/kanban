package domain.usecase.workflow.commitWorkflow;
import domain.model.board.Board;
import domain.usecase.board.BoardDTOConverter;
import domain.usecase.repository.IBoardRepository;

public class CommitWorkflowUseCase {
    private IBoardRepository boardRepository;

    public CommitWorkflowUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public void execute(CommitWorkflowInput input, CommitWorkflowOutput output) {
        Board board = BoardDTOConverter.toEntity(boardRepository.findById(input.getBoardId()));
        board.addWorkflow(input.getWorkflowId());

        boardRepository.save(BoardDTOConverter.toDTO(board));
    }
}
