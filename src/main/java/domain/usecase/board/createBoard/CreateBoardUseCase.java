package domain.usecase.board.createBoard;

import domain.adapter.board.BoardRepository;
import domain.model.board.Board;

public class CreateBoardUseCase {
    private BoardRepository boardRepository;

    public CreateBoardUseCase(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getBoardName(), input.getUsername());
        boardRepository.save(board);

        output.setBoardId(board.getBoardId());
    }
}
