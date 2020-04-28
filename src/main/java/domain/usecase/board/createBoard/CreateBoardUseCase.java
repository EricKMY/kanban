package domain.usecase.board.createBoard;

import domain.model.board.Board;
import domain.usecase.repository.IBoardRepository;

public class CreateBoardUseCase {
    private IBoardRepository iBoardRepository;

    public CreateBoardUseCase(IBoardRepository iBoardRepository) {
        this.iBoardRepository = iBoardRepository;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getBoardName(), input.getUsername());
        iBoardRepository.save(board);

        output.setBoardId(board.getId());
    }
}
