package domain.usecase.board.createBoard;

import domain.model.board.Board;
import domain.usecase.board.BoardDTOConverter;
import domain.usecase.repository.IBoardRepository;

public class CreateBoardUseCase {
    private IBoardRepository boardRepository;

    public CreateBoardUseCase(IBoardRepository iBoardRepository) {
        this.boardRepository = iBoardRepository;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getBoardName(), input.getUsername());
        boardRepository.save(BoardDTOConverter.toDTO(board));

        output.setBoardId(board.getId());
    }
}
